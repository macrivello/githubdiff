package co.cognized.githubdiff.di

import co.cognized.githubdiff.net.GithubApi
import dagger.Component

@Component(modules = [NetworkModule::class])
interface ApiInjector {
    fun provideApi() : GithubApi
}