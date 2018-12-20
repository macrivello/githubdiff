package co.cognized.githubdiff.ui.diff

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import co.cognized.githubdiff.R
import co.cognized.githubdiff.di.NetworkModule
import co.cognized.githubdiff.model.GithubRepoPR
import co.cognized.githubdiff.ui.BaseActivity
import io.reactivex.disposables.Disposable
import io.reflectoring.diffparser.api.UnifiedDiffParser
import okhttp3.*
import java.io.IOException


class PRDiffActivity : BaseActivity() {
    lateinit var prDiffRecycler: RecyclerView
    lateinit var pullRequest: GithubRepoPR
    lateinit var repoName: String
    lateinit var prNameText: TextView

    val diffParser = UnifiedDiffParser()
    val TAG = PRDiffActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pullRequest = intent.getSerializableExtra("pull_request") as GithubRepoPR
        repoName = intent.getSerializableExtra("repo_name") as String

        supportActionBar?.setTitle(repoName)

        setContentView(R.layout.pr_diff_activity)
        prNameText = findViewById(R.id.pr_name_text)
        prNameText.text = pullRequest.title
        prNameText.setOnClickListener { click() }

        prDiffRecycler = findViewById(R.id.pr_diff_recycler)
        prDiffRecycler.layoutManager = LinearLayoutManager(this)
        prDiffRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        prDiffRecycler.adapter = PRDiffAdapter(this, pullRequest)

        fetchDiff()
    }

    private fun click() {
        Log.d("","")
    }

    private fun fetchDiff() {
        isLoading(true)

        val client = OkHttpClient();
                client.newCall(Request.Builder()
                .url(pullRequest.diff_url)
                .addHeader(NetworkModule.AUTH_HEADER, NetworkModule.AUTH_HEADER_VAL)
                .build()).enqueue( object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            isLoading(false)
                        }}
                    override fun onResponse(call: Call, response: Response) {
                        val bytes = response.body()?.bytes()
                        runOnUiThread {
                            loadDiffList(bytes)
                        }
                    }
                })
    }

    private fun loadDiffList(res: ByteArray?) {
        isLoading(false)
        (prDiffRecycler.adapter as PRDiffAdapter).updateDiffList(diffParser.parse(res))
    }

    private fun isLoading(b: Boolean) {

    }
}