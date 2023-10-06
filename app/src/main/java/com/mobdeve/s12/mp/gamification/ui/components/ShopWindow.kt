package com.mobdeve.s12.mp.gamification.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.Cosmetic
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.modifiers.advancedShadow
import com.mobdeve.s12.mp.gamification.ui.theme.MOBDEVEProjectTheme
import com.mobdeve.s12.mp.gamification.ui.theme.Background
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor

@Composable
fun ShopWindow(profile : Profile, cosmeticsList : ArrayList<Cosmetic>) {
    MOBDEVEProjectTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Background
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                )
                Card(
                    colors = CardDefaults.cardColors (
                        containerColor = SecondaryColor
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                        .advancedShadow(
                            Color.Black,
                            offsetX = 10.dp,
                            offsetY = 5.dp,
                            spread = 4.dp,
                            blurRadius = 5.dp,
                            borderRadius = 5.dp
                        )
                        .height(650.dp)
                        .fillMaxWidth()
                ) {
                    Box (
                        modifier = Modifier
                            .background(SecondaryColor)
                            .padding(10.dp)
                    ) {
                        CosmeticList(cosmeticsList)
                    }
                }
            }
        }
    }
}