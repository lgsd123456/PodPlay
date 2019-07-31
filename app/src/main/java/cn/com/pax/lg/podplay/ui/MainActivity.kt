package cn.com.pax.lg.podplay.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cn.com.pax.lg.podplay.R
import cn.com.pax.lg.podplay.adapter.PodcastListAdapter
import cn.com.pax.lg.podplay.repository.ItunesRepo
import cn.com.pax.lg.podplay.service.ItunesService
import cn.com.pax.lg.podplay.service.PodcastResponse
import cn.com.pax.lg.podplay.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), PodcastListAdapter.PodcastListAdapterListener {
    override fun onShowDetails(podcastSummaryViewData: SearchViewModel.PodcastSummaryViewData) {

    }

    val TAG = javaClass.simpleName

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var podcastListAdapter: PodcastListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
//        val itunesService = ItunesService.instance
//        val itunesRepo = ItunesRepo(itunesService)
//        itunesRepo.searchByTerm("Android Developer") {
//            Log.i(TAG, "Results = $it")
//        }
        setupViewModels()
        updateControls()

        handleIntent(intent)
    }

    private fun setupViewModels() {
        val service = ItunesService.instance
        searchViewModel = ViewModelProviders.of(this).get(
            SearchViewModel::class.java)
        searchViewModel.iTunesRepo = ItunesRepo(service)
    }

    private fun updateControls() {
        podcastRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        podcastRecyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
                podcastRecyclerView.context, layoutManager.orientation)
        podcastRecyclerView.addItemDecoration(dividerItemDecoration)
        podcastListAdapter = PodcastListAdapter(null, this, this)
        podcastRecyclerView.adapter = podcastListAdapter
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inference = menuInflater
        inference.inflate(R.menu.menu_serach, menu)

        val searchMenuItem = menu?.findItem(R.id.search_item)
        val searchView = searchMenuItem?.actionView as SearchView

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent!!)
    }

    private fun handleIntent(intent: Intent) {
        if(Intent.ACTION_SEARCH == intent.action) {
            val quey = intent.getStringExtra(SearchManager.QUERY)
            performSearch(quey)
        }
    }

    private fun performSearch(term: String) {
        showProgressBar()
        searchViewModel.searchPodcasts(term) {
            results ->
            hideProgressBar()
            toolbar.title = getString(R.string.search_results)
            podcastListAdapter.setSearchData(results)
        }
//        val itunesService = ItunesService.instance
//        val itunesRepo = ItunesRepo(itunesService)
//
//        itunesRepo.searchByTerm(term) {
//            Log.i(TAG, "Results = $it")
//        }
    }
}
