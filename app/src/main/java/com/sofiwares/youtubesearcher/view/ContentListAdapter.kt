package com.sofiwares.youtubesearcher.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sofiwares.youtubesearcher.util.DurationFormatter
import com.sofiwares.youtubesearcher.util.PublishedDateFormatter
import com.sofiwares.youtubesearcher.R
import com.sofiwares.youtubesearcher.model.ContentModel

/**
 * Created by Ido Sofi on 11/24/2017.
 */
class ContentListAdapter(private var mDataSet: ArrayList<ContentModel>) : RecyclerView.Adapter<ContentViewHolder>() {

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContentViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.video_card_view_item, parent, false)

        return ContentViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContentViewHolder?, position: Int) {
        val isPlaylist = mDataSet[position].isPlaylist

        holder?.title?.text = mDataSet[position].title
        holder?.duration?.text =
                when {
                    isPlaylist -> "PlayList"
                    DurationFormatter.format(mDataSet[position].duration) == "00:00" -> "Live"
                    else -> DurationFormatter.format(mDataSet[position].duration)
                }
        holder?.publishedDate?.text = PublishedDateFormatter.format(mDataSet[position].publishedDate)

        // Load the image using glide library
        Glide.with(holder?.itemView?.context).load(mDataSet[position].thumbNailURL).into(holder?.thumbNailImage)
    }

    fun loadNewItems(items: ArrayList<ContentModel>?) {
        if (items != null && items != mDataSet) {
            mDataSet.clear()
            mDataSet.addAll(items)
            notifyDataSetChanged()
        }
    }
}