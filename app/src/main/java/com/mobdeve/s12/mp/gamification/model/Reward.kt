package com.mobdeve.s12.mp.gamification.model

data class Reward(
    val skill : Skill,
    val xp: Float
) {}

fun createDefaultSkillReward() : Reward{
    val sk = Skill("Rewarded skill", "Description", )
    return Reward(sk, 10.0f)
}