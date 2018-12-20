package co.cognized.githubdiff.ui.repo

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import co.cognized.githubdiff.R
import co.cognized.githubdiff.model.GithubRepo
import co.cognized.githubdiff.model.GithubRepoPR
import co.cognized.githubdiff.ui.BaseActivity
import co.cognized.githubdiff.ui.diff.PRDiffActivity
import co.cognized.githubdiff.ui.home.ProcoreRepoListActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RepoPRListActivity : BaseActivity(), GithubRepoPullsListAdapter.OnClickListener {
    lateinit var repoPullsRecycler: RecyclerView
    lateinit var repo: GithubRepo
    var subscription: Disposable? = null
    val TAG = ProcoreRepoListActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = intent.getSerializableExtra("repo") as GithubRepo
        supportActionBar?.title = repo.name

        setContentView(R.layout.repo_pr_list_activity)

        repoPullsRecycler = findViewById(R.id.repo_pulls_recycler)
        repoPullsRecycler.layoutManager = LinearLayoutManager(this)
        repoPullsRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        repoPullsRecycler.adapter = GithubRepoPullsListAdapter(this, this)

        fetchPRs()
    }

    private fun fetchPRs() {
        isLoading(true)
        subscription = gitHubApi
                .getProcoreRepoPulls(repo.name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { isLoading(false) }
                .subscribe(
                        { res -> loadPRList(res) },
                        { e -> Log.e(TAG, e?.message) })
    }

    private fun loadPRList(res: List<GithubRepoPR>) {
        (repoPullsRecycler.adapter as GithubRepoPullsListAdapter).updatePullRequests(res)
    }

    private fun isLoading(b: Boolean) {
        // TODO add/remove loading indicator
    }

    override fun onClick(pr: GithubRepoPR) {
        // launch new activity
        val intent = Intent(this, PRDiffActivity::class.java)
        intent.putExtra("pull_request", pr)
        intent.putExtra("repo_name", repo.name)
        startActivity(intent)
    }
}
