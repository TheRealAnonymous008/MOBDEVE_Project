package com.mobdeve.s12.mp.gamification.model

class SkillListHolder(val skills : ArrayList<Skill> = ArrayList<Skill>()) {
    fun add(s: Skill){
        skills.add(s)
    }

    fun remove(pos : Int){
        skills.removeAt(pos)
    }

    fun update(pos : Int, payload : Skill){
        skills[pos] = payload
    }
}