package com.mobdeve.s12.mp.gamification.ui.components.cosmetics

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mobdeve.s12.mp.gamification.model.Cosmetic
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.ProfileViewModel
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor
import com.mobdeve.s12.mp.gamification.ui.theme.OtherAccent
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

class ImageSizeParameters {
    companion object {
        val IMAGE_SIZE = 50.dp
        val BOX_SIZE = 100.dp
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CosmeticEntry(
    cosmetic : Cosmetic,
    profile : Profile,
    onProfileUpdate : (Profile) -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }

    if(showDialog.value) {
        CosmeticDialog(
            cosmetic = cosmetic,
            showState = showDialog.value,
            onDismissRequest = {showDialog.value = false},
            profile,
            onProfileUpdate)
    }
    CosmeticContainer(cosmetic) {
        showDialog.value = !showDialog.value
    }
}

@Composable
fun CosmeticContainer(
    cosmetic : Cosmetic,
    onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = SecondaryColor
            ),
            modifier = Modifier
                .height(ImageSizeParameters.BOX_SIZE)
                .width(ImageSizeParameters.BOX_SIZE)
                .padding(7.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = PrimaryColor
                ),
                modifier = Modifier
                    .background(PrimaryColor)
                    .fillMaxSize(),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(ImageSizeParameters.BOX_SIZE)
                        .height(ImageSizeParameters.BOX_SIZE)
                        .clickable(onClick = onClick)
                ) {
                    cosmetic.ViewCosmetic(
                        modifier = Modifier
                            .width(ImageSizeParameters.IMAGE_SIZE)
                            .height(ImageSizeParameters.IMAGE_SIZE)
                    )
                }
            }
        }
        Text (
            text = cosmetic.name,
            fontSize = 10.sp,
            color = TextColor,
        )
    }
}

@Composable
fun CosmeticDialog(cosmetic: Cosmetic, showState : Boolean, onDismissRequest: () -> Unit, profile: Profile, onProfileUpdate : (Profile) -> Unit) {
    val isDialogVisible = remember {
        mutableStateOf(true)
    }
    if(!isDialogVisible.value) return

    val profileViewModel: ProfileViewModel = ProfileViewModel(context = LocalContext.current)
    if(showState) {
        Dialog(
            onDismissRequest = {onDismissRequest() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = OtherAccent
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(unbounded = true),

            ) {
                Row {
                    Button(
                        onClick = {
                            isDialogVisible.value = false },
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "close button", tint = Color.Black)
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.25f)
                            .background(Color.Transparent)
                            .fillMaxHeight()
                            .padding(10.dp, 0.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    )
                    {
                        Text(
                            text = profile.profileDetails.currency.toString(),
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = Color.Black,
                            textAlign = TextAlign.Right
                        )
                        androidx.compose.material3.Icon(
                            Icons.Default.Star,
                            contentDescription = "currency indicator",
                            tint = Color.Yellow
                        )
                    }
                }

                Column (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box (
                        modifier = Modifier
                            .padding(10.dp)
                            .border(1.dp, Color.Black)
                    ) {
                        cosmetic.ViewCosmetic(modifier = Modifier.size(100.dp))
                    }

                    Text(
                        text = cosmetic.name,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center)

                    Row {
                        Text(
                            text = cosmetic.cost.toString(),
                            fontSize = 20.sp
                        )
                        Icon(Icons.Default.Star, contentDescription = "currency indicator", tint = Color.Yellow)
                    }
                    Text(
                        text = cosmetic.description,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(10.dp))


                    if(!cosmetic.owned) {
                        TextButton( onClick = {
                            profile.profileDetails.removeCurrency(cosmetic.cost)
                            profile.cosmetics.add(cosmetic)
                            onProfileUpdate(profile)
                            profileViewModel.updateCurrency(profile.profileDetails.currency)
                        },
                        ) {
                            Text("Purchase", modifier = Modifier.background(AccentColor))
                        }
                    }

                }

            }
        }
    }

}
