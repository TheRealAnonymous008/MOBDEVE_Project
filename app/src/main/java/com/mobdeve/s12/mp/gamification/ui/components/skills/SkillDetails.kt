package com.mobdeve.s12.mp.gamification.ui.components.skills

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.ui.components.tasks.TaskRewardsList
import com.mobdeve.s12.mp.gamification.ui.components.tasks.TimeInfoDetails
import com.mobdeve.s12.mp.gamification.ui.theme.OtherAccent
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun SkillDetailsLaoyut(skill : Skill, profile : Profile, onDelete : () -> Unit) {
    var title by remember { mutableStateOf(skill.name) }
    var description by remember { mutableStateOf(skill.description) }

    Column(
        modifier = Modifier
            .background(SecondaryColor)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title of the Task
        BasicTextField(
            value = title,
            onValueChange = {
                skill.name = it
                title = it
            },
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor,
            ),
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        BasicTextField(
            value = description,
            onValueChange = {
                description= it
                skill.description = it
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = TextColor,
            ),
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(16.dp))

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