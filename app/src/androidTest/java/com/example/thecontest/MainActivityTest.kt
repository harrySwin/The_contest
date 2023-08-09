package com.example.thecontest

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private var homeDisplay = 0
    private var awayDisplay = 0
    private var reset = 0
    private var homeButton = 0
    private var awayButton = 0

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
    = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initValidString() {
        homeDisplay = R.id.scoreHome
        awayDisplay = R.id.scoreAway
        reset = R.id.reset
        homeButton = R.id.homeScored
        awayButton = R.id.awayScored
    }

    @Test
    fun clickHomeScoreButton3Times() {
        // given: open app
        // when: click home score 3 times
        // then: score is 3 - 0
        val homeScoreButton = onView(withId(homeButton))

        for (i in 1..3) {
            homeScoreButton.perform(click())
        }

        val homeDisplay = onView(withId(homeDisplay))
        homeDisplay.check(matches(withText("3")))
    }

    @Test
    fun clickHomeAndAwayScoreButton3Times() {
        // given: open app
        // when: home score clicked 3 times and away score clicked 3 times
        // then: score is 3 - 3
        val homeScoreButton = onView(withId(homeButton))
        val awayScoreButton = onView(withId(awayButton))

        for (i in 1..3) {
            homeScoreButton.perform(click())
            awayScoreButton.perform(click())
        }

        val homeDisplay = onView(withId(homeDisplay))
        homeDisplay.check(matches(withText("3")))
        val awayDisplay = onView(withId(awayDisplay))
        awayDisplay.check(matches(withText("3")))
    }
    @Test
    fun testWinConditionForHome() {
        // given: open app
        // when: home score clicked 15 times
        // then: score is 15 - 0, score reset to 0 - 0
        val homeScoreButton = onView(withId(homeButton))

        for (i in 1..15) {
            homeScoreButton.perform(click())
        }

        val homeDisplay = onView(withId(homeDisplay))
        homeDisplay.check(matches(withText("15")))
    }
    @Test
    fun testWinConditionForAway() {
        // given: open app
        // when: away score clicked 15 times
        // then: score is 15 - 0, score reset to 0 - 0
        val awayScoreButton = onView(withId(awayButton))

        for (i in 1..15) {
            awayScoreButton.perform(click())
        }

        val awayDisplay = onView(withId(awayDisplay))
        awayDisplay.check(matches(withText("15")))
    }
    @Test
    fun testResetButton() {
        // given: open app
        // when: home score clicked 3 times, away score clicked 3 times, reset button clicked
        // then: score is 0 - 0
        val homeScoreButton = onView(withId(homeButton))
        val awayScoreButton = onView(withId(awayButton))
        val reset = onView(withId(reset))

        for (i in 1..3) {
            homeScoreButton.perform(click())
            awayScoreButton.perform(click())
        }

        reset.perform(click())

        val homeDisplay = onView(withId(homeDisplay))
        homeDisplay.check(matches(withText("0")))
        val awayDisplay = onView(withId(awayDisplay))
        awayDisplay.check(matches(withText("0")))
    }
    @Test
    fun testScoreOnRotation() {
        // given: open app
        // when: home and away score clicked 3 times and rotate device
        // then: score is 3 - 3
        val homeScoreButton = onView(withId(homeButton))
        val awayScoreButton = onView(withId(awayButton))

        for (i in 1..3) {
            homeScoreButton.perform(click())
            awayScoreButton.perform(click())
        }

        val homeDisplay = onView(withId(homeDisplay))
        homeDisplay.check(matches(withText("3")))
        val awayDisplay = onView(withId(awayDisplay))
        awayDisplay.check(matches(withText("3")))

        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        homeDisplay.check(matches(withText("3")))
        awayDisplay.check(matches(withText("3")))
    }
    @Test
    fun testScoreOnRotationWithClick() {
        // given: open app
        // when: home and away score clicked 3 times and rotate device, click score 1 time
        // then: score is 3 - 3
        val homeScoreButton = onView(withId(homeButton))
        val awayScoreButton = onView(withId(awayButton))

        for (i in 1..3) {
            homeScoreButton.perform(click())
            awayScoreButton.perform(click())
        }

        val homeDisplay = onView(withId(homeDisplay))
        val awayDisplay = onView(withId(awayDisplay))

        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        homeScoreButton.perform(click())
        awayScoreButton.perform(click())
        homeDisplay.check(matches(withText("4")))
        awayDisplay.check(matches(withText("4")))
    }
}