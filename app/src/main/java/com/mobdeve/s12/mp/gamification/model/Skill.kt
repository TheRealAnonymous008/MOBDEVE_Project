package com.mobdeve.s12.mp.gamification.model

import android.util.Log
import com.mobdeve.s12.mp.gamification.R
import kotlin.reflect.typeOf

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
    var level : Int = 0,
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
    )
}
