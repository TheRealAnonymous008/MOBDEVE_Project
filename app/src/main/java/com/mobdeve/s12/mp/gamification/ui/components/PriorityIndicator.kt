package com.mobdeve.s12.mp.gamification.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobdeve.s12.mp.gamification.model.SkillPriority
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor

@Composable
fun PriorityIndicator(priority : SkillPriority){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(AccentColor),
        text = priority.name
    )
}
