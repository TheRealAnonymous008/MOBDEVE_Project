package com.mobdeve.s12.mp.gamification.model

class TaskListHolder(val tasks : ArrayList<Task>) {
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

    fun remove(pos : Int){
        tasks.removeAt(pos)
    }

    fun update(pos : Int, payload : Task){
        tasks[pos] = payload
    }
}