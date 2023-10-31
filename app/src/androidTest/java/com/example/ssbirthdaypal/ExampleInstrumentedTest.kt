package com.example.ssbirthdaypal

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get: Rule
    val activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {

        val email = "simonsone14@gmail.com"
        val pass = "12345678"
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.ssbirthdaypal", appContext.packageName)

        onView(withId(R.id.editText_email)).perform(typeText(email))
        onView(withId(R.id.editText_password)).perform(typeText(pass))

        onView(withId(R.id.buttonLogin)).perform(click())


        Thread.sleep(3000)
        onView(withId(R.id.textview_message)).check(matches(withText("Welcome $email")))


        onView(withId(R.id.fab)).perform(click())
        Thread.sleep(3000)

        onView(withId(R.id.editText_name)).perform(typeText("SS test Friend Name"))
        closeSoftKeyboard()

        onView(withId(R.id.editText_remark)).perform(typeText("SS test Remark"))
        closeSoftKeyboard()

        onView(withId(R.id.button_add)).perform(click())
        Thread.sleep(3000)

        onView(withId(R.id.textview_list_item_title)).check(matches(withText("SS test Friend Name")))
    }
}
