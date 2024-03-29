package com.mobdeve.s12.mp.gamification.ui.components.skills

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.model.SkillPriority
import com.mobdeve.s12.mp.gamification.ui.theme.HighPriority
import com.mobdeve.s12.mp.gamification.ui.theme.LowPriority
import com.mobdeve.s12.mp.gamification.ui.theme.MediumPriority
import com.mobdeve.s12.mp.gamification.ui.theme.NoPriority


fun getPriorityColor(priority : SkillPriority) : Color {
    var bgcolor = NoPriority
    if (priority == SkillPriority.LOW) {
        bgcolor = LowPriority
    } else if (priority == SkillPriority.MEDIUM) {
        bgcolor = MediumPriority
    } else if (priority == SkillPriority.HIGH) {
        bgcolor = HighPriority
    }
    return bgcolor
}

@Composable
fun PriorityIndicator(
    modifier: Modifier = Modifier,
    priority : SkillPriority
){
    var bgcolor = getPriorityColor(priority)


    Card (
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = bgcolor
        ),

        ) {
        Text(
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp, top = 2.dp, bottom = 2.dp),
            text = priority.name,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
