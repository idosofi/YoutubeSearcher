package com.sofiwares.youtubesearcher.util

import com.google.api.client.util.DateTime
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ido Sofi on 11/25/2017.
 */
object PublishedDateFormatter : IFormatter<DateTime> {
    override fun format(value: DateTime): String {
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return "Published on: ${dateFormatter.format(value.value)}"
    }
}