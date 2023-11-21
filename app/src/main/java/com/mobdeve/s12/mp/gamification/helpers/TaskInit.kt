package com.mobdeve.s12.mp.gamification.helpers

import com.mobdeve.s12.mp.gamification.model.Reward
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TimeInfo
import com.mobdeve.s12.mp.gamification.model.createDefaultSkillReward
import java.sql.Timestamp
import kotlin.random.Random


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
            val skidx = Random.nextInt(pool.size)
            val sk = pool[skidx]
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