package com.mobdeve.s12.mp.gamification.model

import java.io.Serializable
import java.sql.Timestamp

data class Task(
    var id : Long,
    var title : String,
    var description: String,
    var timeInfo : TimeInfo,
    var isFinished : Boolean = false
) : Serializable {
    private var currentTimestamp = Timestamp(System.currentTimeMillis())
    private val rewards : ArrayList<Reward> = ArrayList<Reward>()

    fun addReward (reward : Reward) {
        rewards.add(reward)
        // When adding a reward check if the task is finished and if it is, update skill accordingly
        if (isFinished) {
            reward.skill.levelUp(reward.xp)
        }
    }

    fun deleteReward(reward : Reward) {
        rewards.remove(reward)
    }

    fun clearRewards() {
        rewards.clear()
    }
    fun setRewards(other : ArrayList<Reward>) {
        rewards.clear()
        for (reward in other) {
            rewards.add(reward)
        }

    }
    fun getRewards() : ArrayList<Reward> {
        return rewards
    }

    fun finish() {
        for (reward in rewards) {
            reward.skill.levelUp(reward.xp)
        }
        isFinished = true
    }

    fun unfinish() {
        for (reward in rewards) {
            reward.skill.levelDown(reward.xp)
        }
        isFinished = false
    }

    fun updateProgress()  {
        timeInfo.addProgress(1)
    }
    fun getProgress() : Float {
        return timeInfo.getNormalizedProgress()
    }

    fun play() {
        currentTimestamp = getCurrentTimeStamp()
    }

    fun pause() {
        // Do Nothing
    }

    fun isMappedToSkill(sk : Skill) : Boolean {
        for (reward in rewards) {
            if (reward.skill == sk)
                return true
        }
        return false
    }
}

fun createEmptyTask() : Task {
    return Task(
        -1,
        "",
        "",
        TimeInfo(
            getCurrentTimeStamp()
        )
    )
}


fun getCurrentTimeStamp() : Timestamp{
    return Timestamp(System.currentTimeMillis())
}