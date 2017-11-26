package com.sofiwares.youtubesearcher.util

import com.google.api.client.util.DateTime
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by Ido Sofi on 11/25/2017.
 */
class PublishedDateFormatterTest {
    @Test
    fun formatTest() {
        assertTrue(PublishedDateFormatter.format(DateTime(123456)).startsWith("Published on:"))
    }

}