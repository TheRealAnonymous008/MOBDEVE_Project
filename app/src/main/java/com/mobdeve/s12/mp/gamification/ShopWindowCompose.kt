package com.mobdeve.s12.mp.gamification

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.createDefaultCosmeticList
import com.mobdeve.s12.mp.gamification.ui.components.ShopWindow

class ShowWindowCompose : ComponentActivity() {

}

@Preview(showBackground = true)
@Composable
fun PreviewShopWindow() {
    val profile = Profile("Hello",
        "Generic Description",
        R.drawable.download, currency = 10)

    val cosmeticList = createDefaultCosmeticList()

    ShopWindow(profile, cosmeticList)
}