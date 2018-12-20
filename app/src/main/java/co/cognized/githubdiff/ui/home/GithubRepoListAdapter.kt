package co.cognized.githubdiff.ui.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.cognized.githubdiff.model.GithubRepo

class GithubRepoListAdapter(val context: Context, listener: OnClickListener?) : RecyclerView.Adapter<GithubRepoListAdapter.GithubRepoVH>() {

    interface OnClickListener {
        fun onClick(repo: GithubRepo)
    }

    var repos: List<GithubRepo> = emptyList()

    var listener: OnClickListener? = listener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoVH {
        return GithubRepoVH(GithubRepoListItem(context))
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: GithubRepoVH, position: Int) {
        val repo = repos.get(position)
        holder.view.updateRepo(repo)
        holder.view.setOnClickListener { listener?.onClick(repo) }
    }

    fun updateRepos(repos: List<GithubRepo>) {
        this.repos = repos
        notifyDataSetChanged()
    }

    class GithubRepoVH(val view: GithubRepoListItem) : RecyclerView.ViewHolder(view)
}