package co.cognized.githubdiff.ui.repo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import co.cognized.githubdiff.model.GithubRepoPR

class GithubRepoPullsListAdapter(val context: Context, listener: OnClickListener) : RecyclerView.Adapter<GithubRepoPullsListAdapter.GithubRepoPRVH>() {
    var pullRequests: List<GithubRepoPR> = emptyList()

    var listener: OnClickListener? = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoPRVH {
        return GithubRepoPRVH(GithubRepoPRListItem(context))
    }

    override fun getItemCount(): Int {
       return pullRequests.size
    }

    override fun onBindViewHolder(holder: GithubRepoPRVH, position: Int) {
        val pullRequest = pullRequests.get(position)
        holder.view.updatePR(pullRequest)
        holder.view.setOnClickListener { listener?.onClick(pullRequest) }
    }

    fun updatePullRequests(pullRequests: List<GithubRepoPR>) {
        this.pullRequests = pullRequests
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(pullRequest: GithubRepoPR)
    }

    class GithubRepoPRVH(val view: GithubRepoPRListItem) : RecyclerView.ViewHolder(view)

}
