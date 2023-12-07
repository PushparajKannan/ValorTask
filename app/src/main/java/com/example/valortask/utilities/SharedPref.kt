package com.example.valortask.utilities

import android.content.Context
import android.content.SharedPreferences

class SharedPref(val context: Context) {

    private val PREFS_NAME = "ValorSharedPref"
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    val KEY_USER_LOGGEDIN = "userLoggedIn"
    val KEY_USER_NUMBER = "userPhoneNumber"
    val KEY_STATE_TAX = "stateTax"
    val KEY_CUSTOM_FEE = "customFee"



    var loggedInUserNumber: String?
        set(value) = writeStrin(KEY_USER_NUMBER, value)
        get() = readString(KEY_USER_NUMBER, null)

    var stateTax: String?
        set(value) = writeStrin(KEY_STATE_TAX, value)
        get() = readString(KEY_STATE_TAX, "0")

    var customFee: String?
        set(value) = writeStrin(KEY_CUSTOM_FEE, value)
        get() = readString(KEY_CUSTOM_FEE, "0")

    var isUerLoggedIn: Boolean
        set(value) = write(KEY_USER_LOGGEDIN, value)
        get() = readBoolean(KEY_USER_LOGGEDIN, false)


    fun readString(key: String, value: String?): String? {
        return prefs.getString(key, value)
    }

    fun writeStrin(key: String, value: String?) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }

    fun write(key: String, value: Boolean) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putBoolean(key, value)
            commit()
        }
    }


    fun readBoolean(key: String, value: Boolean): Boolean {
        return prefs.getBoolean(key, value)
    }


    fun clear() {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        prefsEditor.clear()
        prefsEditor.apply()
    }

}