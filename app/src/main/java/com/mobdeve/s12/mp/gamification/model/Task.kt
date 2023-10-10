package com.mobdeve.s12.mp.gamification.model

import java.sql.Timestamp
import kotlin.random.Random

data class Task(
    var title : String,
    var description: String,
    var timeInfo : TimeInfo,
    var rewards : ArrayList<Reward> = ArrayList<Reward>(),
    var isFinished : Boolean = true
) {
    fun finish() {
        isFinished = true
    }

    fun unfinish() {
        isFinished = false
    }
}

fun createDefaultTask(pool : ArrayList<Skill> = ArrayList<Skill>()): Task {
    val rw = ArrayList<Reward>()

    for (i in 1 .. 3) {
        if(pool.size < 0) {
            val sk = pool[Random.nextInt(pool.size)];
            rw.add(createDefaultSkillReward(sk))
        }
        else {
            rw.add(createDefaultSkillReward())
        }
    }

    return Task(
        "Generic Task",
        "Generic Description of the Task. This description task is to describe the task in a very descriptive manner",
        TimeInfo(
            Timestamp(System.currentTimeMillis()),
            Timestamp(System.currentTimeMillis()),
            Timestamp(System.currentTimeMillis() + 10000000)
        ),
        rw
    )
}

fun createDefaultTaskList(pool : ArrayList<Skill> = ArrayList<Skill>()) : ArrayList<Task>{
    val taskList : ArrayList<Task> = ArrayList<Task>()

    for(i in 1..10) {
        taskList.add(createDefaultTask(pool))
    }
    return taskList
}