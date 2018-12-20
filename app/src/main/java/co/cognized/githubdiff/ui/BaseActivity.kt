package co.cognized.githubdiff.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.cognized.githubdiff.di.DaggerApiInjector
import co.cognized.githubdiff.net.GithubApi

open class BaseActivity : AppCompatActivity() {
    lateinit var gitHubApi : GithubApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gitHubApi = DaggerApiInjector.builder().build().provideApi()
    }
}