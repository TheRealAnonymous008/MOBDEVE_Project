package com.mobdeve.s12.mp.gamification.ui.components.cosmetics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.model.Cosmetic
import com.mobdeve.s12.mp.gamification.model.FeetCosmetic
import com.mobdeve.s12.mp.gamification.model.HeadCosmetic
import com.mobdeve.s12.mp.gamification.model.LegsCosmetic
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.TorsoCosmetic
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor


@Composable
fun CosmeticRow(
    cosmeticList: ArrayList<out Cosmetic>,
    cosmeticContainer: @Composable (cosmetic : Cosmetic) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(1.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            items(cosmeticList) {cosmetic ->
                cosmeticContainer(cosmetic)
            }
        }
    }

}

@Composable
fun CosmeticRowHeader(text : String) {
    Text(
        text = text,
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp),
        color = TextColor,
        fontSize = 20.sp
    )
}

@Composable
fun CosmeticList(cosmeticList : ArrayList<Cosmetic>, profile: Profile, onProfileUpdate : (Profile) -> Unit) {
    val headList : ArrayList<HeadCosmetic> = ArrayList()
    val torsoList : ArrayList<TorsoCosmetic> = ArrayList()
    val legsList : ArrayList<LegsCosmetic> = ArrayList()
    val feetList : ArrayList<FeetCosmetic> = ArrayList()

    for (i in cosmeticList) {
        if (i is HeadCosmetic)
            headList.add(i)
        if (i is TorsoCosmetic)
            torsoList.add(i)
        if (i is LegsCosmetic)
            legsList.add(i)
        if (i is FeetCosmetic)
            feetList.add(i)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CosmeticRowHeader(text = "HEAD")
        CosmeticRow(headList) { cosmetic: Cosmetic -> CosmeticEntry(cosmetic, profile, onProfileUpdate) }
        CosmeticRowHeader(text = "TORSO")
        CosmeticRow(torsoList) { cosmetic: Cosmetic -> CosmeticEntry(cosmetic, profile, onProfileUpdate) }
        CosmeticRowHeader(text = "LEGS")
        CosmeticRow(legsList) { cosmetic: Cosmetic -> CosmeticEntry(cosmetic, profile, onProfileUpdate)  }
        CosmeticRowHeader(text = "FEET")
        CosmeticRow(feetList) { cosmetic: Cosmetic -> CosmeticEntry(cosmetic, profile, onProfileUpdate)  }
    }
}

