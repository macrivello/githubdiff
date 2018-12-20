package co.cognized.githubdiff.ui.diff

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.reflectoring.diffparser.api.model.Diff

class PRDiffAdapter(val context: Context) : RecyclerView.Adapter<PRDiffAdapter.DiffLineVH>() {
    var diffList: List<Diff> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffLineVH {
        return DiffLineVH(DiffLineItem(context))
    }

    override fun getItemCount(): Int {
        return diffList.size
    }

    override fun onBindViewHolder(holder: DiffLineVH, position: Int) {
    }


    class DiffLineVH(val view: DiffLineItem) : RecyclerView.ViewHolder(view){
    }

    fun updateDiffList(diffList: List<Diff>) {
        this.diffList = diffList
        notifyDataSetChanged()
    }

}
