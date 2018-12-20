package co.cognized.githubdiff.ui.diff

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import co.cognized.githubdiff.R
import io.reflectoring.diffparser.api.model.Hunk
import io.reflectoring.diffparser.api.model.Line

class DiffHunkView(context: Context, val headerLines: MutableList<String>, val hunk: Hunk?) : FrameLayout(context) {
    val hunkHeaderText: TextView
    val toLineContainer: LinearLayout
    val fromLineContainer: LinearLayout

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.diff_hunk_view, this)

        hunkHeaderText = root.findViewById(R.id.hunk_header_text)
        toLineContainer = root.findViewById(R.id.to_line_container)
        fromLineContainer = root.findViewById(R.id.from_line_container)

        hunkHeaderText.text = headerLines.first()

        var fromStartingLineNumber = hunk?.fromFileRange!!.lineStart
        var toStartingLineNumber = hunk?.toFileRange!!.lineStart

        hunk?.lines!!.map {

            when(it.lineType) {
                Line.LineType.NEUTRAL -> {
                    fromLineContainer.addView(DiffLine(context, fromStartingLineNumber++, it.content, DiffLine.Type.NEUTRAL))
                    toLineContainer.addView(DiffLine(context, toStartingLineNumber++, it.content, DiffLine.Type.NEUTRAL))
                }
                Line.LineType.FROM -> {
                    fromLineContainer.addView(DiffLine(context, fromStartingLineNumber++, it.content, DiffLine.Type.FROM))
//                    toLineContainer.addView(DiffLine(context, toStartingLineNumber++, it.content, DiffLine.Type.NEUTRAL))
                }
                Line.LineType.TO -> {
//                    fromLineContainer.addView(DiffLine(context, fromStartingLineNumber++, it.content, DiffLine.Type.FROM))
                    toLineContainer.addView(DiffLine(context, toStartingLineNumber++, it.content, DiffLine.Type.TO))
                }
            }

        }

    }
}
