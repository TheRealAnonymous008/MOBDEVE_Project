package com.mobdeve.s12.mp.gamification.localdb

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
@Entity(tableName = "skills")
data class SkillEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val level: Int,
    val xp: Int,
    val priority: SkillPriority,
    val imageId: Int
)

// Queries
@Dao
interface SkillDao {
    @Query("SELECT * FROM skills")
    fun getAll(): List<SkillEntity>

    @Query("SELECT * FROM skills WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<SkillEntity>

    @Insert
    fun insertAll(vararg users: SkillEntity)

    @Delete
    fun delete(user: SkillEntity)
}

// Helper functions for converting to and from Entities

fun getSkillEntity(skill : Skill) : SkillEntity{
    return SkillEntity(
        name = skill.name,
        description = skill.description,
        priority = skill.priority,
        level = skill.level,
        imageId = skill.imageId,
        xp = skill.xp
    )
}

fun getSkillFromEntity(entry : SkillEntity) : Skill{
    return Skill(
        id = entry.id,
        xp = entry.xp,
        imageId = entry.imageId,
        name = entry.name,
        level = entry.level,
        priority = entry.priority,
        description = entry.description
    )
}
