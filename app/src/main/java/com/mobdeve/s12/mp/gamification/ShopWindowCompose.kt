package com.mobdeve.s12.mp.gamification

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mobdeve.s12.mp.gamification.model.ProfileDetails
import com.mobdeve.s12.mp.gamification.model.createDefaultCosmeticList
import com.mobdeve.s12.mp.gamification.ui.components.cosmetics.ShopWindow

class ShowWindowCompose : ComponentActivity() {

}

@Preview(showBackground = true)
@Composable
fun PreviewShopWindow() {
    val profileDetails = ProfileDetails("Hello",
        "Generic Description",
        R.drawable.download, currency = 10)

    val cosmeticList = createDefaultCosmeticList()

    ShopWindow(profileDetails, cosmeticList)
}