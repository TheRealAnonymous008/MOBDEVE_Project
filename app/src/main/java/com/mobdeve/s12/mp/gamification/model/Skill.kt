package com.mobdeve.s12.mp.gamification.model

import java.io.Serializable

enum class SkillPriority {
    NONE,
    LOW,
    MEDIUM,
    HIGH
}

data class Skill(
    var id : Int = -1,
    var name : String,
    var description : String = "",
    var level : Int = 0,
    var xp : Int = 0,
    var priority :SkillPriority = SkillPriority.NONE,
    var imageId : Int = -1
) : Serializable {

}

fun createDefaultSkillList() : ArrayList<Skill>{
    val skillList : ArrayList<Skill> = ArrayList<Skill>()

    skillList.add(Skill(0, "Skill 1", "TDNSH DGISDG SBG SBIGSGB SOIG HSOIG LSBOG SHDG SDHG ISODG SJDG SDLG SKOG HSOI GJEG OIEBDJG OSOH GIOSHG OSHG ISOD GBSG H", level = 0, xp=10, priority = SkillPriority.LOW))
    skillList.add(Skill(1, "Skill 2", "Description", level = 1, xp=20, priority = SkillPriority.HIGH))
    skillList.add(Skill(2, "Skill 3", "Description", level = 2, xp=40, priority = SkillPriority.NONE))
    skillList.add(Skill(3, "Skill 4", "Description", level = 3, xp=50, priority = SkillPriority.MEDIUM))

    return skillList
}