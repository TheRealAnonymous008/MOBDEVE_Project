package com.mobdeve.s12.mp.gamification.model

import android.util.Log

class TaskListHolder() {
    val tasks : ArrayList<Task> = ArrayList<Task>()

    fun get(str : String) : Task?{
        for (t in tasks){
            if(t.title == str){
                return t
            }
        }
        return null
    }

    fun getTasks() : List<Task>{
        return tasks.toList()
    }

    fun add(t: Task){
        tasks.add(t)
    }

    fun remove(t : Task){
        Log.e("Hello", t.title)
        tasks.remove(t)
        Log.e("Size", tasks.size.toString())
    }

    fun update(pos : Int, payload : Task){
        tasks[pos] = payload
    }
}