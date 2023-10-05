package com.mobdeve.s12.mp.gamification.model

import java.sql.Timestamp

data class Task(
    var title : String,
    var description: String,
    var timeInfo : TimeInfo,
    var rewards : ArrayList<Reward> = ArrayList<Reward>()
) {}

fun createDefaultTask(): Task {
    val rw = ArrayList<Reward>()
    rw.add(createDefaultSkillReward())
    rw.add(createDefaultSkillReward())
    rw.add(createDefaultSkillReward())

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

fun createDefaultTaskList() : ArrayList<Task>{
    val taskList : ArrayList<Task> = ArrayList<Task>()

    for(i in 1..10) {
        taskList.add(createDefaultTask())
    }
    return taskList
}