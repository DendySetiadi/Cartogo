package com.example.test

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri

class ProfileManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

    fun getProfileImageUri(): Uri? {
        val uriString = sharedPreferences.getString("profileImageUri", null)
        return uriString?.let { Uri.parse(it) }
    }

    fun setProfileImageUri(uri: Uri) {
        sharedPreferences.edit().putString("profileImageUri", uri.toString()).apply()
    }
}