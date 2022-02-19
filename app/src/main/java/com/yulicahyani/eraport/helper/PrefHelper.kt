package com.yulicahyani.eraport.helper

import android.content.Context
import android.content.SharedPreferences

class PrefHelper (context: Context){
    private val PREFS_NAME = "sharedprefEraport"
    private var sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }
    fun put(key: String, value: Int?) {
        if (value != null) {
            editor.putInt(key, value)
                .apply()
        }
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun getInt(key: String): Int {
        return sharedPref.getInt(key, -1)
    }

    fun clear() {
        editor.clear()
            .apply()
    }

}