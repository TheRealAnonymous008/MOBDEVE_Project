package com.mobdeve.s12.mp.gamification.model

data class Reward(
    var skill : Skill,
    var xp: Float
) {}

fun createDefaultSkillReward(sk: Skill = Skill("Rewarded skill", "Description", )) : Reward{
    return Reward(sk, 10.0f)
}