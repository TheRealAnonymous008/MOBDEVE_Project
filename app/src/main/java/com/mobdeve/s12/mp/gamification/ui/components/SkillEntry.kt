package com.mobdeve.s12.mp.gamification.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun SkillEntry(skill : Skill){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryColor)
                .padding(20.dp)
                .padding(start = 40.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(end = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Skill name
                    Text(
                        modifier = Modifier
                            .fillMaxSize(),
                        text = skill.name,
                        color = TextColor
                    )

                    // Skill description
                    Text(
                        modifier = Modifier
                            .fillMaxSize(),
                        text = skill.description,
                        color = TextColor
                    )

                    ProgressBar(min = 0f, max = 100f, value = skill.xp.toFloat())
                }

                Button(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterVertically),
                    onClick = { /*  TODO: Add button click logic */ }
                ) {
                    Text(
                        text = "Play"
                    )
                }
            }
        }
    }
}

@Composable
fun ProgressBar(min : Float, max : Float, value : Float){
    Box(
        modifier = Modifier
            .fillMaxHeight(),
        contentAlignment = Alignment.BottomCenter
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .height(8.dp),
            progress = (value - min) / (max - min),
            trackColor = Color.White,
            color = AccentColor
        )
    }
}