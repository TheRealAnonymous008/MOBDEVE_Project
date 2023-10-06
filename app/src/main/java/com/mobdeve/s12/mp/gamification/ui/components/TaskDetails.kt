package com.mobdeve.s12.mp.gamification.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TimeInfo
import com.mobdeve.s12.mp.gamification.model.createDefaultTask
import com.mobdeve.s12.mp.gamification.model.formatTimestampDate
import com.mobdeve.s12.mp.gamification.model.formatTimestampTime

@Composable
fun TaskDetailsLayout(task : Task) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title of the Task
        Text(
            text = task.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        Text(
            text = task.description,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Scheduled
        Text(
            text = "Scheduled",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        TimeInfoDetails(timeInfo = task.timeInfo)

        Spacer(modifier = Modifier.height(8.dp))

        // Rewards list
        Text(
            text = "Rewards",
            fontSize = 18.sp
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
        ) {
            TaskRewardsList(rewards = task.rewards)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskDetailsLayout() {
    val t = createDefaultTask()
    TaskDetailsLayout(t)
}