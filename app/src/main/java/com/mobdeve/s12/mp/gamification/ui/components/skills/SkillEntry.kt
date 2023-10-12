package com.mobdeve.s12.mp.gamification.ui.components.skills

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun SkillEntry(skill : Skill){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)

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
                    Text(
                        text = skill.name,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(bottom = 4.dp),
                        color = TextColor
                    )

                    PriorityIndicator(
                        priority = skill.priority

                    )
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

