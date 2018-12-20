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
    val PERSONAL_API_TOKEN = "de18a0e4ab5f591c77691b56c6a1d020fdc311ab"
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