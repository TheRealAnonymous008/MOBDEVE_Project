package com.mobdeve.s12.mp.gamification.model

import java.io.Serializable

data class Reward(
    var task: Task,
    var skill : Skill,
    var xp: Int
) : Serializable {}

fun createDefaultSkillReward(t: Task, sk: Skill = Skill(0, "Rewarded skill", "Description", )) : Reward{
    return Reward(t, sk, 10)
}