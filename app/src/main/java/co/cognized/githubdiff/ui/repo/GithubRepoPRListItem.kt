package co.cognized.githubdiff.ui.repo

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import co.cognized.githubdiff.R
import co.cognized.githubdiff.model.GithubRepo
import co.cognized.githubdiff.model.GithubRepoPR

class GithubRepoPRListItem(context: Context) : FrameLayout(context) {
    var pr: GithubRepoPR? = null
    var title: TextView

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.repo_pr_list_item, this)
        title = root.findViewById(R.id.pr_title_text)
    }

    fun updatePR(pr: GithubRepoPR) {
        this.pr = pr
        title.text = pr.title
    }
}
