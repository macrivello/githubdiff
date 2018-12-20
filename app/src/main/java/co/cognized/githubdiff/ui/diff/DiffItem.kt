package co.cognized.githubdiff.ui.diff

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import co.cognized.githubdiff.R
import co.cognized.githubdiff.model.GithubRepo
import io.reflectoring.diffparser.api.model.Diff

class DiffItem(context: Context) : FrameLayout(context){
    var diff: Diff? = null
    val fromFile: TextView
    val toFile: TextView
    val diffHunkContainer: LinearLayout

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.pr_diff_item, this)

        toFile = root.findViewById(R.id.to_file_text)
        fromFile = root.findViewById(R.id.from_file_text)
        diffHunkContainer = root.findViewById(R.id.diff_hunk_container)
    }

    fun updateDiff(diff: Diff) {
        fromFile.text = diff.fromFileName
        toFile.text = diff.toFileName

        for (hunk in diff.hunks) {
            diffHunkContainer.addView(DiffHunkView(context, diff.headerLines, hunk))
        }
    }
}
