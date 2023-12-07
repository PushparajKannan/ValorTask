package com.example.valortask.view.ui.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith
import com.example.valortask.R
import com.example.valortask.view.ui.activity.authentication_module.view.SplashScreenActivity


@RunWith(AndroidJUnit4ClassRunner::class)
class SplashScreenActivityTest{

    @Test
    fun checkTestUI() {
        val activityScenario = ActivityScenario.launch(SplashScreenActivity::class.java)

        onView(withId(R.id.mainLay)).check(matches(isDisplayed()))
    }
}