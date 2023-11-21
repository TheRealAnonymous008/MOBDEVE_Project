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
    var parent : SkillNode? = null,
    var visible : SkillNodeVisibility = SkillNodeVisibility.OPEN
) {
    var children : ArrayList<SkillNode> = ArrayList<SkillNode>()

    override fun toString(): String {
        return "$skill whose children are $children"
    }

    fun addChild(sk : SkillNode) {
        if (sk.parent !== null) {
            sk.parent!!.children.remove((sk))
        }

        sk.parent = this
        this.children.add(sk)
    }
}

