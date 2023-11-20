package com.mobdeve.s12.mp.gamification.ui.components.skills

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillEntry(skill : Skill, profile : Profile, onUpdate: (s : Skill) -> Unit, onDelete : (s : Skill) -> Unit){
    var isShowingTaskDetails by remember { mutableStateOf(false) }

    if (isShowingTaskDetails) {
        Dialog(
            onDismissRequest = {
                isShowingTaskDetails = false
                onUpdate(skill)
            }) {
            SkillDetailsLaoyut(skill = skill , profile = profile, onDelete = {
                isShowingTaskDetails = false
                profile.skills.remove(skill)
                onDelete(skill)
            })
        }
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        onClick = {
            isShowingTaskDetails = true
        }

    ) {
        // Skill Title, Priority, and Image
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp)
        ) {
            // Image
            Image(
                painter = painterResource(id = skill.imageId),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding()
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    var name = skill.name
                    if (name.isBlank())
                        name = "Untitled Skill"
                    Text(
                        text = name,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(0.7f)
                            .padding(bottom = 4.dp),
                        color = TextColor
                    )

                    Box(
                        modifier = Modifier.weight(0.7f)
                    ) {
                        PriorityIndicator(
                            priority = skill.priority
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Progress Bar
                ProgressBar(min = 0f, max = 100f, value = skill.xp.toFloat() )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = skill.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            color = TextColor,
            fontSize = 12.sp
        )
    }
}


