package com.sofiwares.youtubesearcher.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sofiwares.youtubesearcher.util.DurationFormatter
import com.sofiwares.youtubesearcher.util.PublishedDateFormatter
import com.sofiwares.youtubesearcher.R
import com.sofiwares.youtubesearcher.model.VideoModel

/**
 * Created by Ido Sofi on 11/24/2017.
 */
class VideoListAdapter(private var mDataSet: ArrayList<VideoModel>) : RecyclerView.Adapter<VideoViewHolder>() {

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VideoViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.video_card_view_item, parent, false)

        return VideoViewHolder(v)
    }

    override fun onBindViewHolder(holder: VideoViewHolder?, position: Int) {
        val isPlaylist = mDataSet[position].isPlaylist

        holder?.title?.text = mDataSet[position].title
        holder?.duration?.text = if (isPlaylist) "PlayList" else DurationFormatter.format(mDataSet[position].duration)
        holder?.publishedDate?.text = PublishedDateFormatter.format(mDataSet[position].publishedDate)

        // Load the image using glide library
        Glide.with(holder?.itemView?.context).load(mDataSet[position].thumbNailURL).into(holder?.thumbNailImage)
    }

    fun loadNewItems(items: ArrayList<VideoModel>?) {
        if (items != null && items != mDataSet) {
            mDataSet.clear()
            mDataSet.addAll(items)
            notifyDataSetChanged()
        }
    }
}