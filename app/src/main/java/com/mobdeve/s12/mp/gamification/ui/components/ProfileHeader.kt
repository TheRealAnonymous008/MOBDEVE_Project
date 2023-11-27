package com.mobdeve.s12.mp.gamification.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobdeve.s12.mp.gamification.MainActivity
import com.mobdeve.s12.mp.gamification.model.ProfileDetails
import com.mobdeve.s12.mp.gamification.model.ProfileViewModel
import com.mobdeve.s12.mp.gamification.modifiers.advancedShadow
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileHeader(profileDetails: ProfileDetails, navController: NavController) {
    val profileViewModel: ProfileViewModel = ProfileViewModel(context = LocalContext.current)
    val profileDetails by profileViewModel.profileDetails

    Row(
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
        ) {
            profileDetails.avatar.ConstructAvatar(
                modifier = Modifier
                    .width(ProfileHeaderParameters.IMAGE_SIZE)
                    .height(ProfileHeaderParameters.IMAGE_SIZE),
                background = SecondaryColor
            )
        }

        Spacer(modifier = Modifier.width(10.dp))
        // Other half
            Card(
                colors = CardDefaults.cardColors(TertiaryColor),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .advancedShadow(
                        Color.Black,
                        offsetX = 5.dp,
                        offsetY = 5.dp,
                        spread = 4.dp,
                        blurRadius = 5.dp
                    )
                    .background(Color.Transparent),
                shape = RectangleShape
            ) {
                Row {
                    Column(
                        modifier = Modifier
                            .background(TertiaryColor)
                            .padding(10.dp)
                            .weight(0.75f)
                    ) {
                        // Username
                        BasicTextField(
                            value = profileDetails.name,
                            cursorBrush = SolidColor(Color.Unspecified),
                            maxLines = 1,
                            onValueChange = { newName ->
                                profileViewModel.updateName(newName)
                            },
                            textStyle = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextColor,
                            ),
                            modifier = Modifier
                                .fillMaxWidth(.50f)
                                .background(TertiaryColor)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Description
                        BasicTextField(
                            cursorBrush = SolidColor(Color.Unspecified),
                            modifier = Modifier
                                .fillMaxWidth(.50f)
                                .background(TertiaryColor),
                            value = profileDetails.description,
                            textStyle = TextStyle(
                                fontSize = 10.sp,
                                color = TextColor
                            ),
                            onValueChange = { newDescription ->
                                profileViewModel.updateDescription(newDescription)
                            },

                            )
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.25f)
                            .background(Color.Transparent)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Text(
                            text = profileDetails.currency.toString(),
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "currency indicator",
                            tint = Color.Yellow
                        )
                    }
                }

            }


    }
}

