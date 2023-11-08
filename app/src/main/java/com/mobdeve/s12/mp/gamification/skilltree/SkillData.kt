package com.mobdeve.s12.mp.gamification.skilltree

import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.SkillPriority

fun createDefaultSkillList() : ArrayList<Skill>{
    val skillList : ArrayList<Skill> = ArrayList<Skill>()

    var skill1 : Skill = Skill(
        0,
        "Mathematics",
        "This is the first skill",
        level = 0, xp=10,
        priority = SkillPriority.LOW)
    var skill2 : Skill = Skill(
        1,
        "Multivariable Calculus",
        "This is a skill for Multivariable Calculus, I like this subject",
        level = 0, xp=10,
        priority = SkillPriority.LOW)
    var skill3 : Skill = Skill(
        2,
        "Linear Algebra",
        "Trying to learn how to make 3D shapes",
        level = 0, xp=10,
        priority = SkillPriority.LOW)
    var skill4 : Skill = Skill(
        3,
        "Matrices",
        "Working with Matrices",
        0, 10,
        SkillPriority.HIGH,
    )
    var skill5: Skill = Skill(
        4,
        "Art",
        "Drawing practice",
        0, 10,
        SkillPriority.LOW
    )

    var skill6: Skill = Skill(
        4,
        "Eigenvalues",
        "Eigen Stuff",
        0, 10,
        SkillPriority.LOW
    )

    var skill7: Skill = Skill(
        4,
        "SVD",
        "Eigen Stuff",
        0, 10,
        SkillPriority.LOW
    )


    skill1.addChild(skill2)
    skill1.addChild(skill3)
    skill3.addChild(skill4)
    skill3.addChild(skill6)
    skill3.addChild(skill7)

    skillList.add(skill1)
    skillList.add(skill5)
    skillList.add(skill2)
    skillList.add(skill3)
    skillList.add(skill4)
    skillList.add(skill6)
    skillList.add(skill7)

    return skillList
}
