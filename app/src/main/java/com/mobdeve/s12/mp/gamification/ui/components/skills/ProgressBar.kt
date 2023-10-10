package com.mobdeve.s12.mp.gamification.ui.components.skills

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor

@Composable
fun ProgressBar(min : Float, max : Float, value : Float){
    val progressValue = (value - min) / (max - min)
    val progressPercentage = progressValue * 100

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            progress = progressValue,
            trackColor = Color.White,
            strokeCap = StrokeCap.Round,
            color = AccentColor
        )
        Spacer(modifier = Modifier.height(height = 0.dp))
        Text (
            text = progressPercentage.toString() + "%",
            fontSize = 8.sp,
            color = Color.Black,
            modifier = Modifier
                .background(Color.Transparent),
            textAlign = TextAlign.Center
        )
    }
}