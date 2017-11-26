package com.sofiwares.youtubesearcher

import android.support.test.runner.AndroidJUnit4
import com.sofiwares.youtubesearcher.youtube.YouTubeAPIManager
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Ido Sofi on 11/25/2017.
 */
@RunWith(AndroidJUnit4::class)
class YouTubeAPIManagerTest {

    private val query = "Hello"

    @Test
    fun searchResultSizeTest() {
       val a = YouTubeAPIManager().search(query)
        if(a.videoList != null) {
            Assert.assertTrue(a.videoList!!.size <= YouTubeAPIManager.MAX_SEARCH_RESULTS)
        }
    }
}