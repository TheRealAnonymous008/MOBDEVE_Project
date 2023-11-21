package com.mobdeve.s12.mp.gamification.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobdeve.s12.mp.gamification.MainActivity
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.modifiers.advancedShadow
import com.mobdeve.s12.mp.gamification.model.ProfileDetails
import com.mobdeve.s12.mp.gamification.ui.theme.Background
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TertiaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor


class ProfileHeaderParameters {
    companion object {
        val IMAGE_SIZE = 75.dp
        val HEADER_SIZE = 100.dp
    }
}

@Composable
fun ProfileHeader(profileDetails : ProfileDetails, navController: NavController, profile: Profile, onProfileUpdate: (Profile) -> Unit){
    var isEditing by remember { mutableStateOf(false) }
    Row  (
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .background(Background)
            .fillMaxWidth()
            .height(ProfileHeaderParameters.HEADER_SIZE)
    ) {
        // Profile Avatar
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 10.dp)
                .advancedShadow(
                    Color.Black,
                    offsetX = 5.dp,
                    offsetY = 5.dp,
                    spread = 4.dp,
                    blurRadius = 5.dp
                )
                .width(ProfileHeaderParameters.HEADER_SIZE)
                .fillMaxHeight()
                .background(PrimaryColor)
                .clickable {
                    navController.navigate(MainActivity.AVATAR_WINDOW)
                }
        ){
            profileDetails.avatar.ConstructAvatar(
                modifier = Modifier
                    .width(ProfileHeaderParameters.IMAGE_SIZE)
                    .height(ProfileHeaderParameters.IMAGE_SIZE),
                background = SecondaryColor
            )
        }

        Spacer(modifier = Modifier.width(10.dp))
        // Other half
        Card (
            modifier = Modifier
                .padding(top = 10.dp)
                .advancedShadow(
                    Color.Black,
                    offsetX = 5.dp,
                    offsetY = 5.dp,
                    spread = 4.dp,
                    blurRadius = 5.dp
                ),

            shape = RectangleShape

        ) {
            Column(
                modifier = Modifier
                    .background(TertiaryColor)
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                if (isEditing) {
                    // Display edit profile details when editing is enabled
                    EditProfileDetails(profile) { updatedProfile ->
                        // Update the profile when the save button is clicked
                        // You might want to save the updated profile to a repository or perform other actions here
                        // For now, just log the updated profile
                        Log.d("Profile Update", updatedProfile.toString())

                        // Disable editing after saving
                        isEditing = false
                    }
                } else {
                    // Display profile details
                    // ... (unchanged)

                    // Edit Button
                    Button(
                        onClick = {
                            // Enable editing when the Edit button is clicked
                            isEditing = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text("Edit Profile")
                    }
                }
            }
        }
    }
                // Username
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = profileDetails.name,
                    color = TextColor,
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Description
                Text(
                    modifier = Modifier
                        .fillMaxSize(),
                    text = profileDetails.description,
                    color = TextColor,
                )
            }




@Composable
fun EditProfileDetails(profile: Profile, onProfileUpdate: (Profile) -> Unit) {
    var editedName by remember { mutableStateOf(profile.profileDetails.name) }
    var editedDescription by remember { mutableStateOf(profile.profileDetails.description) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Edit Name
        TextField(
            value = editedName,
            onValueChange = {
                editedName = it
            },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Edit Description
        TextField(
            value = editedDescription,
            onValueChange = {
                editedDescription = it
            },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Save Button
        Button(
            onClick = {
                // Update the profileDetails with the edited values
                val updatedProfile = profile.copy(
                    profileDetails = profile.profileDetails.copy(
                        name = editedName,
                        description = editedDescription
                    )
                )
                // Save the updated profile using SharedPreferences or any other method
                saveProfile(updatedProfile)
                // Notify the caller about the profile update
                onProfileUpdate(updatedProfile)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}

// Function to save the profile using SharedPreferences
private fun saveProfile(profile: Profile) {
    // Implement your SharedPreferences logic here
}