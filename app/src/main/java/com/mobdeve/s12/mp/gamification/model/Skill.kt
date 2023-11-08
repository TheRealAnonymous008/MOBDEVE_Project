package com.mobdeve.s12.mp.gamification.model

import android.util.Log
import kotlin.reflect.typeOf

enum class SkillPriority {
    NONE,
    LOW,
    MEDIUM,
    HIGH
}

data class Skill(
    val id : Int,
    var name : String,
    var description : String = "",
    var level : Int = 0,
    var xp : Int = 0,
    var priority :SkillPriority = SkillPriority.NONE,
    var imageId : Int = -1,
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