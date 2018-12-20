package co.cognized.githubdiff.model

import java.io.Serializable

data class GithubRepo(val name: String,
                    val url: String,
                    val description: String,
                      val language: String,
                      val stargazers_count: String) : Serializable
