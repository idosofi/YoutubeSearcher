package com.sofiwares.youtubesearcher.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import com.sofiwares.youtubesearcher.ErrorID
import com.sofiwares.youtubesearcher.model.VideoModel
import com.sofiwares.youtubesearcher.youtube.YouTubeAPIManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri


/**
 * Created by Ido Sofi on 11/24/2017.
 */
class VideoListViewModel : ViewModel() {

    var videoList = MutableLiveData<ArrayList<VideoModel>>()
    var searchError = MutableLiveData<ErrorID>()

    init {
        videoList.value = arrayListOf()
        searchError.value = ErrorID.NONE
    }

    fun searchYoutube(query: String) {
        SearchTask(videoList, searchError).execute(query)
    }

    class SearchTask(private var videoList: MutableLiveData<ArrayList<VideoModel>>,
                     private var errorID: MutableLiveData<ErrorID>) : AsyncTask<String, Unit, SearchTaskResult>() {

        override fun doInBackground(vararg args: String?): SearchTaskResult {
            return YouTubeAPIManager().search(args[0] as String)
        }

        override fun onPostExecute(result: SearchTaskResult) {
            if (result.errorID != ErrorID.NONE) {
                errorID.value = result.errorID
                errorID.value = ErrorID.NONE
            }
            else if (result.videoList != null) {
                videoList.value = result.videoList
            }
        }
    }

    data class SearchTaskResult(val videoList: ArrayList<VideoModel>? = null,
                                val errorID: ErrorID = ErrorID.NONE)
}