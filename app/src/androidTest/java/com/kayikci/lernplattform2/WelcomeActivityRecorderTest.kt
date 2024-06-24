package com.kayikci.lernplattform2

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kayikci.lernplattform2.activities.WelcomeActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class WelcomeActivityRecorderTest {





    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(WelcomeActivity::class.java)


    @Test
    fun welcomeActivityRecorderTest() {

        val appCompatButton = onView(
            allOf(
                withId(R.id.register_button), withText("Register User"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.firstnameEditText),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Testvorname"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.lastnameEditText),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("Testnachname"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.emailEditText),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("asdf@asdf.de"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.passwordEditText),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("Passwort0101!"), closeSoftKeyboard())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.registerButton), withText("Benutzer registrieren"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())


        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.username_edit_text),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("asdf@asdf.de"), closeSoftKeyboard())


        val appCompatEditText9 = onView(
            allOf(
                withId(R.id.password_edit_text),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText9.perform(replaceText("Passwort0101!"), closeSoftKeyboard())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.sign_in_button), withText("Sign In"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatButton3.perform(click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val appCompatEditText10 = onView(
            allOf(
                withId(R.id.et_name),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText10.perform(replaceText("testprufungsname"), closeSoftKeyboard())

        val appCompatEditText11 = onView(
            allOf(
                withId(R.id.et_info),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText11.perform(replaceText("testprufungsinfo"), closeSoftKeyboard())

        val appCompatEditText12 = onView(
            allOf(
                withId(R.id.et_beschreibung),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText12.perform(replaceText("testprufungsbeschreibung"), closeSoftKeyboard())

        val appCompatButton4 = onView(
            allOf(
                withId(R.id.btn_add), withText("Hinzufügen"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.question_detail_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatButton4.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.exam_recycler_view),
                childAtPosition(
                    withClassName(`is`("android.widget.FrameLayout")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.questionfab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

        val appCompatEditText13 = onView(
            allOf(
                withId(R.id.et_fragestellung),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.question_detail_container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText13.perform(replaceText("testfragestellung"), closeSoftKeyboard())

        val appCompatEditText14 = onView(
            allOf(
                withId(R.id.et_hinweis),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.question_detail_container),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText14.perform(longClick())

        val appCompatEditText15 = onView(
            allOf(
                withId(R.id.et_hinweis),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.question_detail_container),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText15.perform(replaceText("testaufgabenhinweis"), closeSoftKeyboard())

        val appCompatEditText16 = onView(
            allOf(
                withId(R.id.et_hinweis), withText("testaufgabenhinweis"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.question_detail_container),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText16.perform(click())

        val appCompatEditText17 = onView(
            allOf(
                withId(R.id.et_hinweis), withText("testaufgabenhinweis"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.question_detail_container),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText17.perform(click())

        val appCompatEditText18 = onView(
            allOf(
                withId(R.id.et_loesung),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.question_detail_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText18.perform(replaceText("testmusterlosung"), closeSoftKeyboard())

        val appCompatButton5 = onView(
            allOf(
                withId(R.id.btn_question_add), withText("Hinzufügen"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatButton5.perform(click())

        val appCompatEditText19 = onView(
            allOf(
                withId(R.id.et_beschreibung), withText("testprufungsbeschreibung"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText19.perform(replaceText("testprufungsbeschreibungUpdate"))

        val appCompatEditText20 = onView(
            allOf(
                withId(R.id.et_beschreibung), withText("testprufungsbeschreibungUpdate"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText20.perform(closeSoftKeyboard())

        val appCompatButton6 = onView(
            allOf(
                withId(R.id.btn_update), withText("Updaten"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        5
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatButton6.perform(click())

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.detail_toolbar),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        val appCompatTextView = onView(
            allOf(
                withId(androidx.appcompat.R.id.title), withText("PDF-Export: Alle Klausuren"),
                childAtPosition(
                    childAtPosition(
                        withId(androidx.appcompat.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        val recyclerView2 = onView(
            allOf(
                withId(R.id.exam_recycler_view),
                childAtPosition(
                    withClassName(`is`("android.widget.FrameLayout")),
                    0
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val appCompatButton7 = onView(
            allOf(
                withId(R.id.btn_delete), withText("Löschen"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        5
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatButton7.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.detail_toolbar),
                        childAtPosition(
                            allOf(
                                withId(R.id.collapsing_toolbar),
                                withContentDescription("Prüfungsliste")
                            ),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }


}
