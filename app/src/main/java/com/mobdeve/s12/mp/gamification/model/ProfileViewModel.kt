package com.mobdeve.s12.mp.gamification.model

// ProfileViewModel.kt

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)

    private val _profileDetails = mutableStateOf(getProfileDetails())
    val profileDetails: State<ProfileDetails> get() = _profileDetails

    fun updateName(newName: String) {
        val updatedProfile = _profileDetails.value.copy(name = newName)
        saveProfileDetails(updatedProfile)
    }

    fun updateDescription(newDescription: String) {
        val updatedProfile = _profileDetails.value.copy(description = newDescription)
        saveProfileDetails(updatedProfile)
    }

    fun updateAvatar(newAvatar: Avatar) {
        val updatedProfile = _profileDetails.value.copy(avatar = newAvatar)
        saveProfileDetails(updatedProfile)
    }

    fun updateCurrency(newCurrency: Int) {
        val updatedProfile = _profileDetails.value.copy(currency = newCurrency)
        saveProfileDetails(updatedProfile)
    }

    private fun saveProfileDetails(profileDetails: ProfileDetails) {
        MainScope().launch {
            with(sharedPreferences.edit()) {
                putString("name", profileDetails.name)
                putString("description", profileDetails.description)
                putString("avatar", profileDetails.avatar.toJson())
                apply()
            }
            _profileDetails.value = profileDetails
        }
    }

    private fun getProfileDetails(): ProfileDetails {
        val name = sharedPreferences.getString("name", "") ?: ""
        val description = sharedPreferences.getString("description", "") ?: ""
        val avatarJson = sharedPreferences.getString("avatar", "") ?: ""

        val avatar = Avatar.fromJson(avatarJson)
        val currency = sharedPreferences.getInt("currency", 0)

        return ProfileDetails(name = name, description = description, avatar = avatar, currency = currency)
    }
}
