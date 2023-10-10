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
import com.mobdeve.s12.mp.gamification.model.CosmeticType
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun CosmeticList(cosmeticList : ArrayList<Cosmetic>) {
    val hatsList : ArrayList<Cosmetic> = ArrayList<Cosmetic>()
    val topList : ArrayList<Cosmetic> = ArrayList<Cosmetic>()
    val bottomList : ArrayList<Cosmetic> = ArrayList<Cosmetic>()
    val footwearList : ArrayList<Cosmetic> = ArrayList<Cosmetic>()

    for (i in cosmeticList) {
        when(i.cosmeticType) {
            CosmeticType.HAT -> hatsList.add(i)
            CosmeticType.TOP -> topList.add(i)
            CosmeticType.BOTTOM -> bottomList.add(i)
            CosmeticType.FOOTWEAR -> footwearList.add(i)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "HAT",
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
                items(hatsList) {hat ->
                    CosmeticEntry(hat)
                }

            }
        }


        Text(
            text = "TOP",
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
                items(topList) {top ->
                    CosmeticEntry(top)
                }
            }
        }
        Text(
            text = "BOTTOM",
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
                items(bottomList) {bottom ->
                    CosmeticEntry(bottom)
                }
            }
        }

        Text(
            text = "FOOTWEAR",
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
                items(footwearList) {footwear ->
                    CosmeticEntry(footwear)
                }
            }
        }

    }
}