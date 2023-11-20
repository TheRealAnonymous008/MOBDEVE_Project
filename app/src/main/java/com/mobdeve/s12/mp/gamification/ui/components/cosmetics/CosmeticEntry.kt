package com.mobdeve.s12.mp.gamification.ui.components.cosmetics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mobdeve.s12.mp.gamification.model.Cosmetic
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
    cosmetic : Cosmetic
) {
    val showDialog = remember { mutableStateOf(false) }

    if(showDialog.value) {
        CosmeticDialog(
            cosmetic = cosmetic,
            showState = showDialog.value,
            onDismissRequest = {showDialog.value = false})
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
fun CosmeticDialog( cosmetic: Cosmetic, showState : Boolean, onDismissRequest: () -> Unit) {
    val isDialogVisible = remember {
        mutableStateOf(true)
    }
    if(!isDialogVisible.value) return

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
                Button(
                    onClick = {
                        isDialogVisible.value = false },
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "close button", tint = Color.Black)
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

                    Text(
                        text = cosmetic.cost.toString(),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                        )
                    Text(
                        text = cosmetic.description,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(10.dp))
                }

            }
        }
    }

}
