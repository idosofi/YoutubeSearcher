package com.sofiwares.youtubesearcher.data

import com.google.api.client.util.DateTime
import com.sofiwares.youtubesearcher.model.ContentModel

/**
 * Created by idosofi on 26/11/2017.
 */
class Content(override var title: String,
              override var thumbNailURL: String,
              override var duration: String,
              override var isPlaylist: Boolean,
              override var publishedDate: DateTime,
              override var contentId: String) : ContentModel