package com.sofiwares.youtubesearcher.youtube

import android.support.annotation.WorkerThread
import android.text.TextUtils
import android.util.Log
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.sofiwares.youtubesearcher.data.Content
import com.sofiwares.youtubesearcher.util.ErrorID
import com.sofiwares.youtubesearcher.model.ContentModel
import com.sofiwares.youtubesearcher.viewmodel.ContentListViewModel
import java.io.IOException
import kotlin.collections.ArrayList

/**
 * Created by Ido Sofi on 11/24/2017.
 */
class YouTubeAPIManager {

    companion object {
        val MAX_SEARCH_RESULTS = 10L
        private val APP_NAME = "YoutubeSearcher"
        private val API_KEY = "AIzaSyBsk2xDHFx0xXt_Mi_iIHl6WNksvZ8y4q0"
        private val ISO_8601_DEFAULT = "PT0S"

        private var youtube: YouTube = YouTube.Builder(NetHttpTransport(), JacksonFactory(), HttpRequestInitializer {})
                .setApplicationName(APP_NAME).build()
    }

    @WorkerThread
    fun search(query: String): ContentListViewModel.SearchTaskResult {
        try {
            // Define the API request for retrieving search results.
            val search = youtube.search().list("id,snippet")

            search.key = API_KEY
            search.q = query

            // Restrict the search results to only include video and playlist results
            search.type = "video,playlist"

            // To increase efficiency, only retrieve the fields that the application uses.
            search.fields = "items(snippet/title,snippet/thumbnails/default/url,snippet/publishedAt,id/kind,id/videoId,id/playlistId)"
            search.maxResults = MAX_SEARCH_RESULTS

            // Call the API and print results.
            val searchResponse = search.execute()
            val searchResultListIterator = searchResponse.items.iterator()

            val data = ArrayList<ContentModel>()

            while (searchResultListIterator.hasNext()) {
                val singleVideo = searchResultListIterator.next()
                val isPlaylist = (singleVideo.id.kind == "youtube#playlist")
                val thumbnail = singleVideo.snippet.thumbnails.default

                data.add(Content(
                        title = singleVideo.snippet.title,
                        thumbNailURL = thumbnail.url,
                        duration = ISO_8601_DEFAULT,
                        isPlaylist = isPlaylist,
                        publishedDate = singleVideo.snippet.publishedAt,
                        contentId = if (isPlaylist) singleVideo.id.playlistId else singleVideo.id.videoId))
            }

            val videos = youtube.videos().list("contentDetails")
            videos.key = API_KEY
            videos.fields = "items(contentDetails/duration)"
            val videoIdArray = arrayListOf<String>()
            data.map { videoIdArray.add(it.contentId) }
            videos.id = TextUtils.join(",", videoIdArray)

            val videosResponse = videos.execute()

            val videoItemsIterator = videosResponse.items.iterator()
            for (i in 0 until data.size) {
                if (!data[i].isPlaylist) {
                    val video = videoItemsIterator.next()
                    data[i].duration = video.contentDetails.duration
                }
            }

            return ContentListViewModel.SearchTaskResult(data)

        } catch (e: GoogleJsonResponseException) {
            Log.e("YouTubeAPIManager","There was a service error: " + e.details.code + " : "
                    + e.details.message)
            return ContentListViewModel.SearchTaskResult(errorID =  ErrorID.GENERAL)
        } catch (e: IOException) {
            Log.e("YouTubeAPIManager","There was an IO error: " + e.cause + " : " + e.message)
            return ContentListViewModel.SearchTaskResult(errorID = ErrorID.NO_NETWORK)
        } catch (t: Throwable) {
            t.printStackTrace()
            return ContentListViewModel.SearchTaskResult(errorID = ErrorID.GENERAL)
        }
    }
}