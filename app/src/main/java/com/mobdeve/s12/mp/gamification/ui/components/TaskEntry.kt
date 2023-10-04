package com.mobdeve.s12.mp.gamification.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor


@Composable
fun TaskEntry(task : Task){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryColor)
                .padding(20.dp)
                .padding(start = 40.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(end = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Skill name
                    Text(
                        modifier = Modifier
                            .fillMaxSize(),
                        text = task.title,
                        color = TextColor
                    )

                    // Skill description
                    Text(
                        modifier = Modifier
                            .fillMaxSize(),
                        text = task.description,
                        color = TextColor
                    )

                }

                Button(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterVertically),
                    onClick = { /*  TODO: Add button click logic */ }
                ) {
                    Text(
                        text = "Play"
                    )
                }
            }
        }
    }
}