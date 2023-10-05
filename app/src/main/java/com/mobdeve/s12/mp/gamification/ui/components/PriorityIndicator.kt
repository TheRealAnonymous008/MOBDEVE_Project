package com.mobdeve.s12.mp.gamification.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.SkillPriority
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor

@Composable
fun PriorityIndicator(priority : SkillPriority){
    var bgcolor = Color.Gray
    if (priority == SkillPriority.LOW) {
        bgcolor = Color.Green
    } else if (priority == SkillPriority.MEDIUM) {
        bgcolor = Color.Yellow
    } else if (priority == SkillPriority.HIGH) {
        bgcolor = Color.Red
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgcolor),
        ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp),
            text = priority.name
        )
    }
}
