package co.cognized.githubdiff.ui.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import co.cognized.githubdiff.R
import co.cognized.githubdiff.model.GithubRepo
import co.cognized.githubdiff.ui.BaseActivity
import co.cognized.githubdiff.ui.repo.RepoPRListActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProcoreRepoListActivity: BaseActivity(), GithubRepoListAdapter.OnClickListener {
    lateinit var repoListRecycler: RecyclerView
    var subscription: Disposable? = null
    val TAG = ProcoreRepoListActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.procore_repo_list_activity)

        repoListRecycler = findViewById(R.id.repo_list_recycler)
        repoListRecycler.layoutManager = LinearLayoutManager(this)
        repoListRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        repoListRecycler.adapter = GithubRepoListAdapter(this, this)

        fetchRepos()
    }

    private fun fetchRepos() {
        isLoading(true)
        subscription = gitHubApi
                .getProcoreRepos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { isLoading(false) }
                .subscribe(
                        { res -> loadRepoList(res) },
                        { e -> Log.e(TAG, e?.message) })

    }

    private fun loadRepoList(repos: List<GithubRepo>) {
        (repoListRecycler.adapter as GithubRepoListAdapter).updateRepos(repos)
    }

    private fun isLoading(b: Boolean) {
        // TODO add/remove loading indicator
    }

    fun onClick(v: View) {
        fetchRepos()
    }

    // GithubRepoListAdapter.OnClickListener
    override fun onClick(repo: GithubRepo) {
        // launch new activity
        val intent = Intent(this, RepoPRListActivity::class.java)
        intent.putExtra("repo", repo)
        startActivity(intent)
    }
}

