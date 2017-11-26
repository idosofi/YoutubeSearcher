package com.sofiwares.youtubesearcher.model

import com.google.api.client.util.DateTime

/**
 * Created by Ido Sofi on 11/24/2017.
 */
data class VideoModel(var title: String,
                      var thumbNailURL: String,
                      var duration: String,
                      var isPlaylist: Boolean,
                      var publishedDate: DateTime,
                      var videoId: String)