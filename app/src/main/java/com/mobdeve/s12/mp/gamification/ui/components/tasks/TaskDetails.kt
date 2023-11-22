package com.mobdeve.s12.mp.gamification.ui.components.tasks

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TimeInfo
import com.mobdeve.s12.mp.gamification.model.formatTimestampDate
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor
import com.mobdeve.s12.mp.gamification.ui.theme.OtherAccent
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun TaskDetailsLayout(task : Task, profile : Profile, onDelete : () -> Unit, repo: RepositoryHolder) {
    var title by remember { mutableStateOf(task.title)}
    var description by remember { mutableStateOf(task.description) }
    var timeInfo by remember { mutableStateOf(task.timeInfo) }

    Column(
        modifier = Modifier
            .background(SecondaryColor)
            .fillMaxHeight(0.90f)
            .padding(16.dp)
    ) {
        BasicTextField(
            value = title,
            cursorBrush = SolidColor(Color.White),
            onValueChange = {
                task.title = it
                title = it
            },
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor,
            ),
            modifier = Modifier
                .background(PrimaryColor)
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                ) {
                    if (title.isBlank()) {
                        Text(
                            text = "Untitled Task",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        BasicTextField(
            value = description,
            cursorBrush = SolidColor(Color.White),
            onValueChange = {
                description= it
                task.description = it
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = TextColor,
            ),
            modifier = Modifier
                .background(PrimaryColor)
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                ) {
                    if (description.isBlank()) {
                        Text(
                            text = "Blank Description",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            }

        )

        Spacer(modifier = Modifier.height(16.dp))

        // Scheduled
        Text(
            modifier = Modifier
                .background(OtherAccent)
                .padding(start = 10.dp, end = 10.dp),
            text = "Scheduled",
            fontSize = 18.sp,
            color = TextColor,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Update TimeInfo
        TimeInfoDetails(
            timeInfo = timeInfo,
            onUpdate = { updatedTimeInfo: TimeInfo ->
                timeInfo = updatedTimeInfo
                task.timeInfo = updatedTimeInfo
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Rewards list
        Text(
            text = "Rewards",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(AccentColor)
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
        ) {
            TaskRewardsList(task = task, profile = profile, repo)
        }

        // Delete Button
        Button(
            onClick = {
                onDelete()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text("Delete Task", fontWeight = FontWeight.Bold)
        }
    }
}

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
        var timeFrom by remember { mutableStateOf(timeInfo.datetimeFrom)}
        DateTimeInput(
            timeState = timeFrom,
            onUpdate = {
                timeInfo.datetimeFrom = it

                onUpdate(timeInfo)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Date time to (editable)
        var timeTo by remember { mutableStateOf(timeInfo.dateTimeTo)}
        DateTimeInput(
            timeState = timeTo,
            onUpdate = {
                timeInfo.dateTimeTo = it
                onUpdate(timeInfo)
            }
        )

        // Progress
        Text(
            text = "Progress: ${timeInfo.progress}%",
            fontSize = 14.sp,
            color = TextColor,
        )
    }
}