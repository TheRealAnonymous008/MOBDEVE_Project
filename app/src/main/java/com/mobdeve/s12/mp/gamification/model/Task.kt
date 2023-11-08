package com.mobdeve.s12.mp.gamification.model

import android.util.Log
import java.io.Serializable
import java.sql.Timestamp
import java.time.Duration
import kotlin.random.Random

data class Task(
    var id : Int,
    var title : String,
    var description: String,
    var timeInfo : TimeInfo,
    var rewards : ArrayList<Reward> = ArrayList<Reward>(),
    var isFinished : Boolean = false
) : Serializable {
    private var currentTimestamp = Timestamp(System.currentTimeMillis())

    fun finish() {
        isFinished = true
        // TODO: update skills
    }

    fun unfinish() {
        isFinished = false
        // TODO: update skills
    }

    fun updateProgress()  {
        timeInfo.addProgress(1)
    }
    fun getProgress() : Float {
        return timeInfo.getNormalizedProgress()
    }

    fun play() {
        currentTimestamp = getCurrentTimeStamp()
        Log.e("E", "Hello")
    }

    fun pause() {
        // Do Nothing
    }
}

fun createEmptyTask() : Task {
    return Task(
        -1,
        "Title",
        "Description",
        TimeInfo(
            getCurrentTimeStamp()
        )
    )
}

fun createDefaultTask(pool : ArrayList<Skill> = ArrayList<Skill>(), idx : Int = 0): Task {
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
        idx,
        "Generic Task $idx",
        "Generic Description of the Task. This description task is to describe the task in a very descriptive manner",
        TimeInfo(
            Timestamp(System.currentTimeMillis()),
            Timestamp(System.currentTimeMillis()),
            Timestamp(System.currentTimeMillis() + 1000 * 10)
        ),
        rw
    )
}

fun createDefaultTaskList(pool : ArrayList<Skill> = ArrayList<Skill>()) : ArrayList<Task>{
    val taskList : ArrayList<Task> = ArrayList<Task>()

    for(i in 1..10) {
        taskList.add(createDefaultTask(pool, i = i))
    }
    return taskList
}

fun getCurrentTimeStamp() : Timestamp{
    return Timestamp(System.currentTimeMillis())
}