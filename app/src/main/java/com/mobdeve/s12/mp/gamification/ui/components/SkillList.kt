package com.mobdeve.s12.mp.gamification.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun SkillList(skillList : ArrayList<Skill>){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),

        // TODO: Add padding as necessary

    ) {
        items(skillList) { skill ->
            SkillEntry(skill)
        }
    }
}

@Composable
fun SkillEntry(skill : Skill){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ){
        Box(
            modifier = Modifier
                .background(PrimaryColor)
                .fillMaxWidth()
                .padding(20.dp)
                .padding(start = 40.dp)
        ) {
            Row(

            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    // Skill name
                    Text(
                        text = skill.name,
                        color = TextColor
                    )

                    // Skill description
                    Text(
                        text = skill.description,
                        color = TextColor
                    )
                }

                Column (
                    modifier = Modifier.
                    fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ){
                    Button(
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
}