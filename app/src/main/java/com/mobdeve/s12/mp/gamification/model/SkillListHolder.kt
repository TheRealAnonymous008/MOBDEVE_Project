package com.mobdeve.s12.mp.gamification.model

class SkillListHolder() {
    var skills : ArrayList<Skill> = ArrayList<Skill>()
    fun get(str : String) : Skill?{
        for (sk in skills){
            if(sk.name == str){
                return sk
            }
        }
        return null
    }

    fun find(id : Long) : Skill? {
        for (sk in skills) {
            if (sk.id == id)
                return sk
        }
        return null
    }

    fun add(s: Skill){
        skills.add(s)
    }

    fun remove(s : Skill){
        skills.remove(s)
    }

    fun update(pos : Int, payload : Skill){
        skills[pos] = payload
    }

    fun clear() {
        skills.clear()
    }
}