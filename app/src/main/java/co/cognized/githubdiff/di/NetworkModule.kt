package co.cognized.githubdiff.di

import co.cognized.githubdiff.net.GithubApi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.http.HttpHeaders
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object NetworkModule {
    val BASE_URL = "https://api.github.com"
    val PERSONAL_API_TOKEN = "40b2b674635257e1c5f28bd303063dbdd6b28501"
    val AUTH_HEADER = "Authorization"
    val AUTH_HEADER_VAL = "Bearer " + PERSONAL_API_TOKEN

    @Provides
    @JvmStatic
    internal fun provideApi() : GithubApi {
        // add necessary query params to each request
        val client = OkHttpClient.Builder()
                // Add Auth to request
                .addInterceptor {
                    chain: Interceptor.Chain ->
                    val authorizedRequest = chain.request()
                            .newBuilder()
                            .addHeader(AUTH_HEADER, AUTH_HEADER_VAL)
                            .build()
                    chain.proceed(authorizedRequest)
                }
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()

        return retrofit.create(GithubApi::class.java)
    }
}