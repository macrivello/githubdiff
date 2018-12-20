package co.cognized.githubdiff.model

import java.io.Serializable

data class GitRef (val label: String,
                   val ref: String,
                   val sha: String,
                   val user: GithubUser,
                   val repo: GithubRepo) : Serializable
