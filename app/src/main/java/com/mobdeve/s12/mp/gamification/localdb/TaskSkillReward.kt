package com.mobdeve.s12.mp.gamification.localdb

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.SkillPriority
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TimeInfo
import java.sql.Timestamp

// DB TabLE
@Entity(tableName = "task_skills", primaryKeys = ["taskId", "skillId"])
data class TaskSkillRewardEntity (
    @ColumnInfo(name = "taskId") val taskId: Long, // Foreign key referencing the task
    @ColumnInfo(name = "skillId") val skillId: Long, // Foreign key referencing the skill

    val reward: Float
)

// Queries
@Dao
interface TaskSkillRewardDao {
    @Query("SELECT * FROM task_skills")
    fun getAll(): List<TaskSkillRewardEntity>

    // TODO: Finish this
}

// Helper functions for converting to and from Entities

fun getTaskSkillRewards(task: Task) : List<TaskSkillRewardEntity>{
    var rewards : ArrayList<TaskSkillRewardEntity> = ArrayList<TaskSkillRewardEntity>()

    task.rewards.forEach {
        rewards.add(
            TaskSkillRewardEntity(
                taskId = task.id,
                skillId = it.skill.id,
                reward = it.xp
            )
        )
    }

    return rewards
}
