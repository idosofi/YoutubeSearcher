package com.sofiwares.youtubesearcher.util

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by Ido Sofi on 11/25/2017.
 */
class DurationFormatterTest {
    @Test
    fun formatTest() {
        assertTrue(DurationFormatter.format("PT11S") ==  "00:11")
        assertTrue(DurationFormatter.format("PT11M11S") ==  "11:11")
        assertTrue(DurationFormatter.format("PT11H11M11S") ==  "11:11:11")
        assertTrue(DurationFormatter.format("P11DT11H11M11S") ==  "11:11:11:11")
        assertTrue(DurationFormatter.format("") ==  "00:00")
        assertTrue(DurationFormatter.format("G") ==  "00:00")
    }

}