package com.mobdeve.s12.mp.gamification.ui.components.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobdeve.s12.mp.gamification.model.TimeInfo
import com.mobdeve.s12.mp.gamification.model.formatTimestampDate
import com.mobdeve.s12.mp.gamification.model.formatTimestampTime
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun TimeInfoDetails(timeInfo : TimeInfo){
    // Date and Time
    val containsFrom = timeInfo.containsFrom()
    val containsTo = timeInfo.containsTo()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(0.5f)
        ) {
            if (containsFrom)  Text(
                text = "From:",
                color = TextColor
            )
            if (containsTo) Text(
                text = "To:",
                color = TextColor,
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (containsFrom) Text(
                text = formatTimestampDate(timeInfo.datetimeFrom!!),
                color = TextColor,
            )
            if (containsTo) Text(
                text = formatTimestampDate(timeInfo.dateTimeTo!!),
                color = TextColor,
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (containsFrom) Text(
                text = formatTimestampTime(timeInfo.datetimeFrom!!),
                color = TextColor,
            )
            if (containsTo) Text(
                text = formatTimestampTime(timeInfo.dateTimeTo!!),
                color = TextColor,
            )
        }
    }
}
