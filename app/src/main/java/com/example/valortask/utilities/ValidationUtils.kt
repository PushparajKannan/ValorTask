package com.example.valortask.utilities

import android.util.Patterns
import androidx.core.util.PatternsCompat
import java.util.regex.Pattern

object ValidationUtils {




    fun isPasswordValid(password: String?): Boolean {
        val pwd = Pattern.compile(
            "^" + ".{8,}" +               //at least 8 characters
                    "$"
        )
        return password?.let { pwd.matcher(it).matches() } ?: false
    }




    fun isValidPhoneNumber(phone: String?): Boolean {
        return phone?.length == 10

    }


    fun isValidEmailPatterMatcher(mail: String?) =
        mail?.let { PatternsCompat.EMAIL_ADDRESS.matcher(it).matches() } ?: false



}