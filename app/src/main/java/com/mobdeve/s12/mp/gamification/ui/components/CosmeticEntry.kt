package com.mobdeve.s12.mp.gamification.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.model.Cosmetic
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

class ImageSizeParameters {
    // TODO: Add any parameters here. Preferably, anything involving sizing or padding
    companion object {
        val IMAGE_SIZE = 50.dp
        val BOX_SIZE = 100.dp
    }
}
@Composable
fun CosmeticEntry(cosmetic : Cosmetic) {
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
                ) {
                    Image(
                        painter = painterResource(cosmetic.image),
                        contentDescription = "Cosmetic Image",
                        modifier = Modifier
                            .width(ImageSizeParameters.IMAGE_SIZE)
                            .height(ImageSizeParameters.IMAGE_SIZE),
                        alignment = Alignment.Center,
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