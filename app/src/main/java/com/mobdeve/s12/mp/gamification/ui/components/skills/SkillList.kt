package com.mobdeve.s12.mp.gamification.ui.components.skills

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.SkillListHolder

@Composable
fun SkillList(skillList : SkillListHolder, profile : Profile){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),

        // TODO: Add padding as necessary

    ) {
        items(skillList.skills) { skill ->
            SkillEntry(skill)
        }
    }
}
