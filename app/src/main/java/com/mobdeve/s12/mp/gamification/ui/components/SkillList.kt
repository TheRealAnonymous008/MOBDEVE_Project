package com.mobdeve.s12.mp.gamification.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobdeve.s12.mp.gamification.model.Skill

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
