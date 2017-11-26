package com.sofiwares.youtubesearcher

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.sofiwares.youtubesearcher.view.SearchActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SearchActivityTest {

    @Rule @JvmField
    val mActivityRule = ActivityTestRule(SearchActivity::class.java)

    @Test
    fun testLogoOnStartup() {
        onView(withId(R.id.logoImageView)).check(matches(isDisplayed()))
    }

    @Test
    fun testSearch() {
        onView(withId(R.id.search)).perform(click())
    }
}