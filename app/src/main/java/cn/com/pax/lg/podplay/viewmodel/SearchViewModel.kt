package cn.com.pax.lg.podplay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cn.com.pax.lg.podplay.repository.ItunesRepo
import cn.com.pax.lg.podplay.service.PodcastResponse
import cn.com.pax.lg.podplay.util.DateUtils
import java.util.*

class SearchViewModel(application: Application) :
    AndroidViewModel(application) {

    var iTunesRepo: ItunesRepo? = null

    data class PodcastSummaryViewData(
        var name: String? = "",
        var lastUpdated: String? = "",
        var imageUrl: String? = "",
        var feedUrl: String? = "")

    private fun itunesPodcastToPodcastSummaryView(
        itunesPodcast: PodcastResponse.ItunesPodcast):
            PodcastSummaryViewData {
        return PodcastSummaryViewData(
            itunesPodcast.collectionCensoredName,
            DateUtils.jsonDateToShortDate(itunesPodcast.releaseDate),
            itunesPodcast.artworkUrl30,
            itunesPodcast.feedUrl)
    }

    // 1
    fun searchPodcasts(term: String,
                       callback: (List<PodcastSummaryViewData>) -> Unit) {
// 2
        iTunesRepo?.searchByTerm(term) { results ->
            if (results == null) {
    // 3
                callback(emptyList())
            } else {
    // 4
                val searchViews = results.map { podcast ->
                    itunesPodcastToPodcastSummaryView(podcast)
                }
    // 5
                searchViews.let { it -> callback(it) }
            }
        }
    }
}