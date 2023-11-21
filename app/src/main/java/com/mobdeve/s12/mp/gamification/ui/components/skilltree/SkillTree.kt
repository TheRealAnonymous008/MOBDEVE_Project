package com.mobdeve.s12.mp.gamification.ui.components.skilltree

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.SkillTreeLazyLayout
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.SkillListHolder
import com.mobdeve.s12.mp.gamification.skilltree.SkillNode
import com.mobdeve.s12.mp.gamification.skilltree.SkillViewModel
import com.mobdeve.s12.mp.gamification.skilltree.State
import com.mobdeve.s12.mp.gamification.skilltree.X_SCALE
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
                skillNode.children.forEach {child ->
                    var yChildCenterPos = child.yPos

                    Canvas(modifier = Modifier
                        .fillMaxSize()
                        .offset(40.dp, 25.dp)) {

                        drawLine(
                            Color.Black,
                            Offset(0f, 0f),
                            Offset(X_SCALE.toFloat(), yChildCenterPos.toFloat()),
                            5.0f)
                    }
                }
                skillBox(skillNode)
            }
        }
    }
}