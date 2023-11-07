package com.mobdeve.s12.mp.gamification.ui.components.skilltree

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mobdeve.s12.mp.gamification.SkillTreeLazyLayout
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.SkillListHolder
import com.mobdeve.s12.mp.gamification.skilltree.SkillNode
import com.mobdeve.s12.mp.gamification.skilltree.SkillTreeLazyLayoutItemProvider
import com.mobdeve.s12.mp.gamification.skilltree.SkillTreeLazyLayoutState
import com.mobdeve.s12.mp.gamification.skilltree.SkillTreeLazyListScope
import com.mobdeve.s12.mp.gamification.skilltree.SkillTreeLazyListScopeImpl
import com.mobdeve.s12.mp.gamification.skilltree.State
import com.mobdeve.s12.mp.gamification.skilltree.rememberSkillTreeLazyLayoutState
import com.mobdeve.s12.saquilayan.ethanjared.skilltreedemo.components.skillBox

fun getSkillNodes(skillList : SkillListHolder) : List<SkillNode>{
    val arr = ArrayList<SkillNode>()

    skillList.skills.forEach({
        arr.add(SkillNode(
            skill = it,
            breadth = 1,
            xPos = 1,
            yPos = 1
        ))
    })
    return arr.toList()
}

@Composable
fun SkilLTreeWindow(skillList : SkillListHolder, profile : Profile){
    val lazyLayoutState = rememberSkillTreeLazyLayoutState()
    val state = State(skills = getSkillNodes(skillList))

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
                Log.d("Printed Node",skillNode.toString())
                skillBox(skillNode)
            }
        }
    }
}