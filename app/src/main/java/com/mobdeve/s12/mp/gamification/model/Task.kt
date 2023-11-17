package com.mobdeve.s12.mp.gamification.model

import android.util.Log
import java.io.Serializable
import java.sql.Timestamp
import java.time.Duration
import kotlin.random.Random

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

fun createDefaultTask(pool : ArrayList<Skill> = ArrayList<Skill>(), idx : Long = 0): Task {
    val rw = ArrayList<Reward>()
    val task = Task(
        idx,
        "Generic Task $idx",
        "Generic Description of the Task. This description task is to describe the task in a very descriptive manner",
        TimeInfo(
            Timestamp(System.currentTimeMillis()),
            Timestamp(System.currentTimeMillis()),
            Timestamp(System.currentTimeMillis() + 1000 * 10)
        )
    )

    for (i in 1 .. 3) {
        if(pool.size > 0) {
            val skidx =Random.nextInt(pool.size)
            val sk = pool[skidx];
            rw.add(createDefaultSkillReward(task, sk))
        }
        else {
            rw.add(createDefaultSkillReward(task))
        }
    }
    task.setRewards(rw)

    return task
}

fun createDefaultTaskList(pool : ArrayList<Skill> = ArrayList<Skill>()) : ArrayList<Task>{
    val taskList : ArrayList<Task> = ArrayList<Task>()

    for(i in 1..10) {
        taskList.add(createDefaultTask(pool, idx= i.toLong()))
    }
    return taskList
}

fun getCurrentTimeStamp() : Timestamp{
    return Timestamp(System.currentTimeMillis())
}