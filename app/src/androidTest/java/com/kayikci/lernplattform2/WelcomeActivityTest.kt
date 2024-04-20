package com.kayikci.lernplattform2

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kayikci.lernplattform2.activities.WelcomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WelcomeActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(WelcomeActivity::class.java)

    @Test
    fun testButtonChangesText() {


        // Findet den Button und führt einen Klick aus
        onView(withId(R.id.sign_in_button)).perform(click())

        // Überprüft, ob der Text des TextView geändert wurde
        onView(withId(R.id.textView)).check(matches(withText("Button wurde geklickt!")))
    }
}