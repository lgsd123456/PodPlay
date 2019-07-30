package cn.com.pax.lg.podplay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.com.pax.lg.podplay.R
import cn.com.pax.lg.podplay.repository.ItunesRepo
import cn.com.pax.lg.podplay.service.ItunesService
import cn.com.pax.lg.podplay.service.PodcastResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val itunesService = ItunesService.instance
//        val itunesRepo = ItunesRepo(itunesService)
//        itunesRepo.searchByTerm("Android Developer") {
//            Log.i(TAG, "Results = $it")
//        }
    }
}
