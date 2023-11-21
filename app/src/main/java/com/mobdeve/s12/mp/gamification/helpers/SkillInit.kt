package com.mobdeve.s12.mp.gamification.helpers

import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.SkillPriority

fun createDefaultSkillList() : ArrayList<Skill>{
    val skillList : ArrayList<Skill> = ArrayList<Skill>()

    var skill1 : Skill = Skill(
        1,
        "Mathematics",
        "This is the first skill",
        priority = SkillPriority.LOW)
    var skill2 : Skill = Skill(
        2,
        "Multivariable Calculus",
        "This is a skill for Multivariable Calculus, I like this subject",
        priority = SkillPriority.LOW)
    var skill3 : Skill = Skill(
        3,
        "Linear Algebra",
        "Trying to learn how to make 3D shapes",
        priority = SkillPriority.LOW)

    var skill4 : Skill = Skill(
        4,
        "Matrices",
        "Working with Matrices",
        priority = SkillPriority.HIGH,
    )
    var skill5: Skill = Skill(
        5,
        "Art",
        "Drawing practice",
        priority = SkillPriority.LOW
    )

    var skill6: Skill = Skill(
        6,
        "Eigenvalues",
        "Eigen Stuff",
        priority = SkillPriority.LOW
    )

    var skill7: Skill = Skill(
        7,
        "SVD",
        "Eigen Stuff",
        priority = SkillPriority.LOW
    )


    skill1.addChild(skill2)
    skill1.addChild(skill3)
    skill3.addChild(skill4)
    skill3.addChild(skill6)
    skill3.addChild(skill7)

    skillList.add(skill1)
    skillList.add(skill2)
    skillList.add(skill3)
    skillList.add(skill4)
    skillList.add(skill5)
    skillList.add(skill6)
    skillList.add(skill7)

    return skillList
}
