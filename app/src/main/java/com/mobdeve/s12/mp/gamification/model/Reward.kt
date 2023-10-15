package com.mobdeve.s12.mp.gamification.model

import java.io.Serializable

data class Reward(
    var skill : Skill,
    var xp: Float
) : Serializable {}

fun createDefaultSkillReward(sk: Skill = Skill("Rewarded skill", "Description", )) : Reward{
    return Reward(sk, 10.0f)
}