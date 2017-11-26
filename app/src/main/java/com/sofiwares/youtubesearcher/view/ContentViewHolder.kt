package com.sofiwares.youtubesearcher.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sofiwares.youtubesearcher.R

/**
 * Created by Ido Sofi on 11/24/2017.
 */
class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var thumbNailImage: ImageView = itemView.findViewById(R.id.thumbnail)
    var title: TextView = itemView.findViewById(R.id.title)
    var publishedDate: TextView = itemView.findViewById(R.id.publishDate)
    var duration: TextView = itemView.findViewById(R.id.duration)
}
