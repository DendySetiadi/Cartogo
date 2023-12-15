package com.example.test

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri

class ProfileManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun getProfileImageUri(): Uri? {
        val uriString = sharedPreferences.getString("profileImageUri", null)
        return uriString?.let { Uri.parse(it) }
    }

    fun setProfileImageUri(uri: Uri) {
        sharedPreferences.edit().putString("profileImageUri", uri.toString()).apply()
    }

    var userFname: String
        get() = sharedPreferences.getString(KEY_USER_FNAME, "") ?: ""
        set(value) {
            editor.putString(KEY_USER_FNAME, value).apply()
        }

    var userLname: String
        get() = sharedPreferences.getString(KEY_USER_LNAME, "") ?: ""
        set(value) {
            editor.putString(KEY_USER_LNAME, value).apply()
        }

    var userName: String
        get() = sharedPreferences.getString(KEY_USER_NAME, "") ?: ""
        set(value) {
            editor.putString(KEY_USER_NAME, value).apply()
        }

    var userEmail: String
        get() = sharedPreferences.getString(KEY_USER_EMAIL, "") ?: ""
        set(value) {
            editor.putString(KEY_USER_EMAIL, value).apply()
        }

    var userTelepon: String
        get() = sharedPreferences.getString(KEY_USER_TELEPON, "") ?: ""
        set(value) {
            editor.putString(KEY_USER_TELEPON, value).apply()
        }

    companion object {
        private const val KEY_USER_FNAME = "user_fname"
        private const val KEY_USER_LNAME = "user_lname"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_TELEPON = "user_telepon"
    }
}