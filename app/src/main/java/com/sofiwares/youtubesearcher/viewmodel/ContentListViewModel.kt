package com.sofiwares.youtubesearcher.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import com.sofiwares.youtubesearcher.util.ErrorID
import com.sofiwares.youtubesearcher.model.ContentModel
import com.sofiwares.youtubesearcher.youtube.YouTubeAPIManager


/**
 * Created by Ido Sofi on 11/24/2017.
 */
class ContentListViewModel : ViewModel() {

    var videoList = MutableLiveData<ArrayList<ContentModel>>()
    var searchError = MutableLiveData<ErrorID>()

    init {
        videoList.value = arrayListOf()
        searchError.value = ErrorID.NONE
    }

    fun searchYoutube(query: String) {
        SearchTask(videoList, searchError).execute(query)
    }

    class SearchTask(private var videoList: MutableLiveData<ArrayList<ContentModel>>,
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

    data class SearchTaskResult(val videoList: ArrayList<ContentModel>? = null,
                                val errorID: ErrorID = ErrorID.NONE)
}