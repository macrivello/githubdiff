package co.cognized.githubdiff.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import co.cognized.githubdiff.R
import co.cognized.githubdiff.model.GithubRepo

class GithubRepoListItem(context: Context) : FrameLayout(context) {
    var repo: GithubRepo? = null
    val name: TextView

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.repo_list_item, this)
        name = root.findViewById(R.id.repo_name_text)
    }

    fun updateRepo(repo: GithubRepo) {
        this.repo = repo
        name.text = repo.name
    }
}
