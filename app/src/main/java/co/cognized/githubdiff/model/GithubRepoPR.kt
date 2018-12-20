package co.cognized.githubdiff.model

import java.io.Serializable

data class GithubRepoPR(val number: Int,
                        val state: String,
                        val diff_url: String,
                        val title: String,
                        val user: GithubUser,
                        val base: GitRef,
                        val head: GitRef) : Serializable