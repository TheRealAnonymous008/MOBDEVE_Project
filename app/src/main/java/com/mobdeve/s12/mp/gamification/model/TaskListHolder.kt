package com.mobdeve.s12.mp.gamification.model

class TaskListHolder {
    var tasks : ArrayList<Task> = ArrayList<Task>()

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
        tasks.remove(t)
    }

    fun update(pos : Int, payload : Task){
        tasks[pos] = payload
    }

    fun clear() {
        tasks.clear()
    }

    fun getSorted() : List<Task>{
        return tasks.sortedBy { it.getTimeFinished() }
    }
}