package com.mobdeve.s12.mp.gamification.model

enum class SkillPriority {
    NONE,
    LOW,
    MEDIUM,
    HIGH
}

data class Skill(
    val name : String,
    val description : String = "",
    val level : Int = 0,
    val xp : Int = 0,
    val priority :SkillPriority = SkillPriority.NONE,
    val imageBytes: ByteArray? = null
) {

}

fun createDefaultSkillList() : ArrayList<Skill>{
    val skillList : ArrayList<Skill> = ArrayList<Skill>()

    skillList.add(Skill("Skill 1", "TDNSH DGISDG SBG SBIGSGB SOIG HSOIG LSBOG SHDG SDHG ISODG SJDG SDLG SKOG HSOI GJEG OIEBDJG OSOH GIOSHG OSHG ISOD GBSG H", level = 0, xp=10, priority = SkillPriority.LOW))
    skillList.add(Skill("Skill 2", "Description", level = 1, xp=20, priority = SkillPriority.HIGH))
    skillList.add(Skill("Skill 3", "Description", level = 2, xp=40, priority = SkillPriority.NONE))
    skillList.add(Skill("Skill 4", "Description", level = 3, xp=50, priority = SkillPriority.MEDIUM))

    return skillList
}