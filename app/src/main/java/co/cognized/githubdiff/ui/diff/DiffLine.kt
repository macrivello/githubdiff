package co.cognized.githubdiff.ui.diff

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import co.cognized.githubdiff.R

class DiffLine(context: Context, lineNumber: Int, var content: String, type: Type) : FrameLayout(context) {
    enum class Type { NEUTRAL, TO, FROM, BLANK }

    val lineNumberText: TextView
    val contentText: TextView

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.diff_line_item, this)
        lineNumberText = root.findViewById(R.id.line_number_text)
        contentText = root.findViewById(R.id.content_text)

        when (type) {
            Type.BLANK -> {
                root.setBackgroundColor(ContextCompat.getColor(context, R.color.material_grey_300))
            }
            Type.NEUTRAL -> {
                root.setBackgroundColor(ContextCompat.getColor(context, R.color.material_grey_100))
            }
            Type.TO -> {
                root.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
                content = " + " + content

            }
            Type.FROM -> {
                root.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
                content = " - " + content
            }
        }

        if (type != Type.BLANK) {
            lineNumberText.text = lineNumber.toString()
            contentText.text = content
        }
    }
}
