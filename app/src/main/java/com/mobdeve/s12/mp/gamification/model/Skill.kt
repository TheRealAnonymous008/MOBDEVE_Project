package com.mobdeve.s12.mp.gamification.model

import com.mobdeve.s12.mp.gamification.R


enum class SkillPriority {
    NONE,
    LOW,
    MEDIUM,
    HIGH
}

data class Skill(
    var id : Long,
    var name : String,
    var description : String = "",
    var level : Int = 1,
    var xp : Int = 0,
    var priority :SkillPriority = SkillPriority.NONE,
    var imageId : Int = R.drawable.potato,
    var children : ArrayList<Skill> = ArrayList<Skill>(),
    var parent : Skill? = null
) {

    fun addChild(sk : Skill) {
        children.add(sk)
        sk.parent = this
    }

    fun levelUp(x : Int) {
        var total = getTotalPoints()
        total += x
        updateFromTotal(total)
    }

    fun levelDown (x: Int){
        var total = getTotalPoints()
        total -= x
        updateFromTotal(total)
    }

    private fun getTotalPoints() : Int{
        return level * 100 + xp
    }

    private fun updateFromTotal(total: Int) {
        level = total / 100
        xp = total % 100
    }

    override fun equals(other: Any?): Boolean {
        if (other is Skill) {
            if (this.id == other.id)
                return true
        }
        return false
    }

    override fun toString(): String {
        return "Skill Name: $name"
    }
}

fun createEmptySkill(id : Long = -1) : Skill {
    return Skill(
        id  =  id,
        name = "",
        description = ""
    )
}
