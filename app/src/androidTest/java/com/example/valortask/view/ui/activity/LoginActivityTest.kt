package com.example.valortask.view.ui.activity


import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.valortask.R
import com.example.valortask.view.ui.activity.authentication_module.view.LoginActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {
    companion object {

        val STRING_TO_BE_TYPED = "Espresso"

    }

        /*@JvmStatic
        @BeforeClass
        fun dismissANRSystemDialog() {
          //  val activityUnderTest: MyActivity = activityTestRule.getActivity()

            // Use the 'testing' Context
            // Use the 'testing' Context
            val context: Context = InstrumentationRegistry.getInstrumentation().getTargetContext()
            context.sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))

            //activityScenarioRule.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

            *//* val device = UiDevice.getInstance(getInstrumentation())
             // If running the device in English Locale
             var waitButton = device.findObject(UiSelector().textContains("wait"))
             if (waitButton.exists()) {
                 waitButton.click()
             }
             // If running the device in Japanese Locale
             waitButton = device.findObject(UiSelector().textContains("待機"))
             if (waitButton.exists()) {
                 waitButton.click()
             }*//*
        }
    }*/

        @get:Rule
        var activityScenarioRule = activityScenarioRule<LoginActivity>()

    @Test
    fun onCreateTest() {
        // Type text and then press the button.
       // onView(withId(R.id.tvUserName)).perform(typeText(STRING_TO_BE_TYPED), ViewActions.closeSoftKeyboard())

        onView(allOf(isDescendantOfA(withId(R.id.tvUserName)), isAssignableFrom(EditText::class.java)))
            .perform(typeText(STRING_TO_BE_TYPED),ViewActions.closeSoftKeyboard())

        onView(allOf(isDescendantOfA(withId(R.id.tvPassword)), isAssignableFrom(EditText::class.java)))
            .perform(typeText(STRING_TO_BE_TYPED),ViewActions.closeSoftKeyboard())

        //onView(withId(R.id.tvPassword)).perform(typeText(STRING_TO_BE_TYPED), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btnLogin)).perform(click())

        // Check that the text was changed.
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
    }
}