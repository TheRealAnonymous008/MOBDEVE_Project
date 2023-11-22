package com.mobdeve.s12.mp.gamification.ui.components.skilltree

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mobdeve.s12.mp.gamification.SkillTreeLazyLayout
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.SkillListHolder
import com.mobdeve.s12.mp.gamification.skilltree.SkillViewModel
import com.mobdeve.s12.mp.gamification.skilltree.X_SCALE
import com.mobdeve.s12.mp.gamification.skilltree.Y_DISTANCE
import com.mobdeve.s12.mp.gamification.skilltree.Y_SCALE
import com.mobdeve.s12.mp.gamification.skilltree.rememberSkillTreeLazyLayoutState
import com.mobdeve.s12.mp.gamification.ui.theme.Background
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
                .background(Background),
            state = lazyLayoutState) {
            nodes(state.skills) {skillNode ->
                skillNode.children.forEach {child ->

                    var pos1 = Offset(0f, 0f)
                    var pos2 = Offset(X_SCALE.toFloat(), Y_SCALE.toFloat() * (child.breadth - skillNode.breadth))
                    Canvas(modifier = Modifier
                        .offset(40.dp,25.dp)
                        .zIndex(-0.1f)) {
                        drawLine(
                            Color.White,
                            pos1,
                            pos2,
                            5.0f)
                    }
                    Log.d("POSITON", "${child.skill.name} = ${child.xPos}, ${child.yPos} with Offset $pos2 \n and ${skillNode.skill.name} = ${skillNode.xPos} + ${skillNode.yPos} with Offset $pos1")
                }

                skillBox(skillNode)
            }
        }
    }
}