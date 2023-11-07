package com.mobdeve.s12.mp.gamification.skilltree

import com.mobdeve.s12.mp.gamification.model.Skill

enum class SkillNodeVisibility {
    OPEN,
    HIDDEN
}

data class SkillNode (
    var skill : Skill,
    var breadth : Int,
    var xPos : Int,
    var yPos : Int,
    var children : List<SkillNode>? = null,
    var parent : SkillNode? = null,
    var visible : SkillNodeVisibility = SkillNodeVisibility.OPEN
) {
    override fun toString(): String {
        return "$skill whose children are $children"
    }
}
