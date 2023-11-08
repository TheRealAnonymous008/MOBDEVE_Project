package com.mobdeve.s12.mp.gamification.ui.components.skilltree

import com.mobdeve.s12.mp.gamification.model.SkillListHolder
import com.mobdeve.s12.mp.gamification.skilltree.SkillNode

fun getSkillNodes(skillList : SkillListHolder) : List<SkillNode>{
    val arr = ArrayList<SkillNode>()

    // Get all roots

    skillList.skills.forEach {
        arr.add(
            SkillNode(
                skill = it,
                breadth = 1,
                xPos = 1,
                yPos = 1
            )
        )
    }
    return arr.toList()
}