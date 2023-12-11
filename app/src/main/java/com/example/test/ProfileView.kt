package com.example.test

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileView : ViewModel() {
    var profileManager: ProfileManager? = null
    private val _profileImageUri = MutableLiveData<Uri?>()
    val profileImageUri: LiveData<Uri?> get()= _profileImageUri

    fun updateProfileImageUri(uri: Uri) {
        _profileImageUri.value = uri
    }

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    fun updateUserName(newName: String) {
        _userName.value = newName
    }
}