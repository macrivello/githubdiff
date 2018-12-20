package co.cognized.githubdiff.net

import co.cognized.githubdiff.model.GithubRepo
import co.cognized.githubdiff.model.GithubRepoPR
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    // Get Procore Repos
    @GET("users/procore/repos")
    fun getProcoreRepos() : Observable<List<GithubRepo>>
    // Get Repo
    @GET("repos/procore/{name}")
    fun getProcoreRepo(@Path("name") name: String) : Observable<GithubRepo>
    // Get Repo PRs
    @GET("repos/procore/{name}/pulls?state=all")
    fun getProcoreRepoPulls(@Path("name") name: String) : Observable<List<GithubRepoPR>>
    // Get PR
    @GET("repos/procore/{name}/pulls/{number}")
    fun getProcoreRepoPull(@Path("name") name: String) : Observable<GithubRepoPR>

    /* THIS IS NOT A GITHUB API CALL
    // Get PR Diff
    @GET("repos/procore/{name}/pulls/{number}.diff")
    fun getProcoreRepoPullDiff(@Path("name") name: String, @Path("number") number: Int) : Observable<Any>
    */
}