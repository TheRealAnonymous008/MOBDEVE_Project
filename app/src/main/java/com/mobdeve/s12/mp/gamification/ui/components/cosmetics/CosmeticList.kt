package com.mobdeve.s12.mp.gamification.ui.components.cosmetics

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    cosmeticList: List<Cosmetic>,
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

    Log.d("CosmeticList", cosmeticList.toString())
    val headList  = ArrayList<HeadCosmetic>()
    val torsoList = ArrayList<TorsoCosmetic>()
    val legsList = ArrayList<LegsCosmetic>()
    val feetList = ArrayList<FeetCosmetic>()

    headList.clear()
    torsoList.clear()
    legsList.clear()
    feetList.clear()

    for (i in cosmeticList) {
        if (i is HeadCosmetic && !i.owned)
            headList.add(i)
        if (i is TorsoCosmetic && !i.owned)
            torsoList.add(i)
        if (i is LegsCosmetic && !i.owned)
            legsList.add(i)
        if (i is FeetCosmetic && !i.owned)
            feetList.add(i)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if(headList.isNotEmpty()) {
            CosmeticRowHeader(text = "HEAD")
            CosmeticRow(headList.toList()) { cosmetic: Cosmetic -> CosmeticEntry(cosmetic, profile, onProfileUpdate) }
        }

        if(torsoList.isNotEmpty()) {
            CosmeticRowHeader(text = "TORSO")
            CosmeticRow(torsoList.toList()) { cosmetic: Cosmetic -> CosmeticEntry(cosmetic, profile, onProfileUpdate) }
        }

        if(legsList.isNotEmpty()) {
            CosmeticRowHeader(text = "LEGS")
            CosmeticRow(legsList.toList()) { cosmetic: Cosmetic -> CosmeticEntry(cosmetic, profile, onProfileUpdate)  }
        }

        if(feetList.isNotEmpty()) {
            CosmeticRowHeader(text = "FEET")
            CosmeticRow(feetList.toList()) { cosmetic: Cosmetic -> CosmeticEntry(cosmetic, profile, onProfileUpdate)  }
        }
    }
}

fun extractHeads() {

}

