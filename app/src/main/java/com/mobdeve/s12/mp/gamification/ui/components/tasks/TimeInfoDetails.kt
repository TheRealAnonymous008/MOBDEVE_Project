package com.mobdeve.s12.mp.gamification.ui.components.tasks

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.model.TimeInfo
import com.mobdeve.s12.mp.gamification.model.formatTimestampDate
import com.mobdeve.s12.mp.gamification.model.formatTimestampTime
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor
import kotlin.math.round


@Composable
fun TimeInfoDetails(timeInfo: TimeInfo, onUpdate: (TimeInfo) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Date time created (read-only)
        Text(
            text = "Created: ${formatTimestampDate(timeInfo.datetimeCreated)}",
            fontSize = 14.sp,
            color = TextColor,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Date time from (editable)
        var timeFrom by remember { mutableStateOf(timeInfo.datetimeFrom) }
        DateTimeInput(
            timeStamp = timeFrom,
            onUpdate = {
                val old = timeInfo.datetimeFrom
                timeInfo.datetimeFrom = it
                if (! timeInfo.isValid()) {
                    timeInfo.datetimeFrom = old
                }
                onUpdate(timeInfo)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Date time to (editable)
        var timeTo by remember { mutableStateOf(timeInfo.dateTimeTo) }
        DateTimeInput(
            timeStamp = timeTo,
            onUpdate = {
                if (timeInfo.isValid())  {
                    val old = timeInfo.dateTimeTo
                    timeInfo.dateTimeTo = it
                    if (! timeInfo.isValid()) {
                        timeInfo.dateTimeTo = old
                    }
                    onUpdate(timeInfo)
                }
            }
        )

        // Progress
        Text(
            text = "Progress: ${round(timeInfo.getNormalizedProgress() * 100f)}%",
            fontSize = 14.sp,
            color = TextColor,
        )
    }
}