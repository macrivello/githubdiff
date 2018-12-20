package co.cognized.githubdiff.ui.diff

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

    val diffParser = UnifiedDiffParser()
    var subscription: Disposable? = null
    val TAG = PRDiffActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pullRequest = intent.getSerializableExtra("pull_request") as GithubRepoPR
        repoName = intent.getSerializableExtra("repo_name") as String

        setContentView(R.layout.pr_diff_activity)

        prDiffRecycler = findViewById(R.id.pr_diff_recycler)
        prDiffRecycler.layoutManager = LinearLayoutManager(this)
        prDiffRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        prDiffRecycler.adapter = PRDiffAdapter(this)

        fetchDiff()
    }

    private fun fetchDiff() {
        isLoading(true)

        val client = OkHttpClient();
                client.newCall(Request.Builder()
                .url(pullRequest.diff_url)
                .addHeader(NetworkModule.AUTH_HEADER, NetworkModule.AUTH_HEADER_VAL)
                .build()).enqueue( object : Callback {
                    override fun onFailure(call: Call, e: IOException) { isLoading(false) }
                    override fun onResponse(call: Call, response: Response) { loadDiffList(response.body()?.bytes())}
                })
    }

    private fun loadDiffList(res: ByteArray?) {
        isLoading(false)
        val diff = diffParser.parse(res)
    }

    private fun isLoading(b: Boolean) {

    }
}