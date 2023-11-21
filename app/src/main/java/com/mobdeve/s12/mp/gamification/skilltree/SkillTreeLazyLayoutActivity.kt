package com.mobdeve.s12.mp.gamification.skilltree

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mobdeve.s12.mp.gamification.SkillTreeLazyLayout
import com.mobdeve.s12.saquilayan.ethanjared.skilltreedemo.components.skillBox

@Composable
fun SkillTreeLazyLayoutActivity(state: State, actions: Actions) {
    val lazyLayoutState = rememberSkillTreeLazyLayoutState()

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
