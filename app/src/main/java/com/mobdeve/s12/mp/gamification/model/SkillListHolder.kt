package com.mobdeve.s12.mp.gamification.model

class SkillListHolder() {
    val skills : ArrayList<Skill> = ArrayList<Skill>()
    fun get(str : String) : Skill?{
        for (sk in skills){
            if(sk.name == str){
                return sk
            }
        }
        return null
    }

    fun add(s: Skill){
        skills.add(s)
    }

    fun remove(pos : Int){
        skills.removeAt(pos)
    }

    fun update(pos : Int, payload : Skill){
        skills[pos] = payload
    }

    fun clear() {
        skills.clear()
    }
}