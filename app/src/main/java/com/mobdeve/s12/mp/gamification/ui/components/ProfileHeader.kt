package com.mobdeve.s12.mp.gamification.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.ui.theme.Background
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor


class ProfileHeaderParameters {
    // TODO: Add any parameters here. Preferably, anything involving sizing or padding

    companion object {
        val IMAGE_SIZE = 64.dp
        val HEADER_SIZE = 80.dp
    }
}

@Composable
fun ProfileHeader(profile : Profile){
    Row(
        modifier = Modifier
            .background(Background)
            .fillMaxWidth()
            .height(ProfileHeaderParameters.HEADER_SIZE)
    ) {
        // Profile Avatar
        Image(painter = painterResource(profile.avatarId),
            contentDescription = "Profile Avatar",
            modifier = Modifier
                .width(ProfileHeaderParameters.IMAGE_SIZE)
                .fillMaxHeight()
                .background(SecondaryColor)
                .size(ProfileHeaderParameters.IMAGE_SIZE, ProfileHeaderParameters.IMAGE_SIZE),
            contentScale = ContentScale.FillBounds
        )

        // Other half
        Column(
            modifier = Modifier
                .background(PrimaryColor)
                .fillMaxSize()
        ) {
            // Username
            Text(
                modifier = Modifier
                    .background(PrimaryColor)
                    .fillMaxWidth(),
                text = profile.name,
                color = TextColor,
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Description
            Text(
                modifier = Modifier
                    .background(PrimaryColor)
                    .fillMaxSize(),
                text = profile.description,
                color = TextColor,
            )
        }
    }
}