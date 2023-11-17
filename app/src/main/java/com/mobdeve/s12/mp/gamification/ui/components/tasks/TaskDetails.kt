package com.mobdeve.s12.mp.gamification.ui.components.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.localdb.AppDatabase
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.createDefaultTask
import com.mobdeve.s12.mp.gamification.model.generateDefaultProfile
import com.mobdeve.s12.mp.gamification.ui.theme.OtherAccent
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun TaskDetailsLayout(task : Task, profile : Profile, onDelete : () -> Unit, db: AppDatabase) {
    var title by remember { mutableStateOf(task.title)}
    var description by remember { mutableStateOf(task.description) }

    Column(
        modifier = Modifier
            .background(SecondaryColor)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = title,
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
                .background(Color.Transparent)
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
            onValueChange = {
                description= it
                task.description = it
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = TextColor,
            ),
            modifier = Modifier
                .background(Color.Transparent)
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

        TimeInfoDetails(timeInfo = task.timeInfo)

        Spacer(modifier = Modifier.height(16.dp))

        // Rewards list
        Text(
            text = "Rewards",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor
        )

        Box(
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .fillMaxWidth()
        ) {
            TaskRewardsList(task = task, profile = profile, db = db)
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