package co.cognized.githubdiff.ui.diff

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import co.cognized.githubdiff.model.GithubRepoPR
import io.reflectoring.diffparser.api.model.Diff

class PRDiffAdapter(val context: Context, val githubRepoPR: GithubRepoPR) : RecyclerView.Adapter<PRDiffAdapter.DiffVH>() {
    var diffList: List<Diff> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffVH {
        val view = DiffItem(context)
        view.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        return DiffVH(view)
    }

    override fun getItemCount(): Int {
        return diffList.size
    }

    override fun onBindViewHolder(holder: DiffVH, position: Int) {
        val diff = diffList.get(position)
        holder.view.updateDiff(diff)
    }

    class DiffVH(val view: DiffItem) : RecyclerView.ViewHolder(view)

    fun updateDiffList(diffList: List<Diff>) {
        this.diffList = diffList
        notifyDataSetChanged()
    }
}
