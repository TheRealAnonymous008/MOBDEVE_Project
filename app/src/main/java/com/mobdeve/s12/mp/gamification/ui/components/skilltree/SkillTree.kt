package com.mobdeve.s12.mp.gamification.ui.components.skilltree

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mobdeve.s12.mp.gamification.SkillTreeLazyLayout
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.SkillListHolder
import com.mobdeve.s12.mp.gamification.skilltree.SkillNode
import com.mobdeve.s12.mp.gamification.skilltree.SkillViewModel
import com.mobdeve.s12.mp.gamification.skilltree.State
import com.mobdeve.s12.mp.gamification.skilltree.rememberSkillTreeLazyLayoutState
import com.mobdeve.s12.saquilayan.ethanjared.skilltreedemo.components.skillBox



@Composable
fun SkillTreeWindow(skillList : SkillListHolder, profile : Profile){
    val lazyLayoutState = rememberSkillTreeLazyLayoutState()
    val skillViewModel = SkillViewModel(skillList.skills)
    val state = skillViewModel.state.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SkillTreeLazyLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray),
            state = lazyLayoutState) {
            nodes(state.skills) {skillNode ->
                skillBox(skillNode)
            }
        }
    }
}