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
import com.mobdeve.s12.mp.gamification.model.TorsoCosmetic
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun CosmeticList(cosmeticList : ArrayList<Cosmetic>) {
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
        Text(
            text = "HEAD",
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp),
            color = TextColor,
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier
                .padding(1.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                items(headList) {head ->
                    CosmeticEntry(head)
                }

            }
        }


        Text(
            text = "TORSO",
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp),
            color = TextColor,
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier
                .padding(1.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                items(torsoList) {torso ->
                    CosmeticEntry(torso)
                }
            }
        }
        Text(
            text = "LEGS",
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp),
            color = TextColor,
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier
                .padding(1.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                items(legsList) {legs ->
                    CosmeticEntry(legs)
                }
            }
        }

        Text(
            text = "FEET",
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp),
            color = TextColor,
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier
                .padding(1.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                items(feetList) {feet ->
                    CosmeticEntry(feet)
                }
            }
        }

    }
}