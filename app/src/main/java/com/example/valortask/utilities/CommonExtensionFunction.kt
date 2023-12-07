package com.example.valortask.utilities

import com.google.android.material.textfield.TextInputLayout

class CommonExtensionFunction {
}

fun TextInputLayout.checkValidEmail(text: String?) {

    when {
        text.isNullOrEmpty() -> {
            isErrorEnabled = false
            error = null
        }
        ValidationUtils.isValidEmailPatterMatcher(text) -> {
            isErrorEnabled = false
            error = null
        }
        else -> {
            isErrorEnabled = true
            error = "Email must be a valid email"
        }
    }

}


fun TextInputLayout.checkValidPasswordLogin(text: String?) {
    when {
        text.isNullOrEmpty() -> {
            isErrorEnabled = false
            error = null
        }
        /* ValidationUtils.isPasswordValid(text)->{
             isErrorEnabled = false
             error = null
         }
         else ->{
             isErrorEnabled = true
            error = "must be 8+ characters"
         }*/
        ValidationUtils.isPasswordValid(text) -> {
            isErrorEnabled = false
            error = null
        }
        else -> {
            isErrorEnabled = true
            error = "Must be 8+ characters, number and special character"
        }
    }

}