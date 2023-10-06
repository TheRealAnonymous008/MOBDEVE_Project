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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.modifiers.advancedShadow
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.ui.theme.Background
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TertiaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor


class ProfileHeaderParameters {
    // TODO: Add any parameters here. Preferably, anything involving sizing or padding
    companion object {
        val IMAGE_SIZE = 75.dp
        val HEADER_SIZE = 100.dp
    }
}

@Composable
fun ProfileHeader(profile : Profile){


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
                .advancedShadow(Color.Black,
                    offsetX = 5.dp,
                    offsetY = 5.dp,
                    spread = 4.dp,
                    blurRadius = 5.dp)
                .width(ProfileHeaderParameters.HEADER_SIZE)
                .fillMaxHeight()
                .background(PrimaryColor)

        ){
            Image(painter = painterResource(profile.avatarId),
                contentDescription = "Profile Avatar",
                modifier = Modifier
                    .width(ProfileHeaderParameters.IMAGE_SIZE)
                    .height(ProfileHeaderParameters.IMAGE_SIZE)
                    .background(SecondaryColor)
                    .size(ProfileHeaderParameters.IMAGE_SIZE, ProfileHeaderParameters.IMAGE_SIZE),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillBounds
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
                // Username
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = profile.name,
                    color = TextColor,
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Description
                Text(
                    modifier = Modifier
                        .fillMaxSize(),
                    text = profile.description,
                    color = TextColor,
                )
            }
        }

    }
}