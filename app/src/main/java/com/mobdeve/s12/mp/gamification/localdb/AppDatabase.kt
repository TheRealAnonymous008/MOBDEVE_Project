package com.mobdeve.s12.mp.gamification.localdb

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mobdeve.s12.mp.gamification.model.Reward
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.createDefaultTaskList
import com.mobdeve.s12.mp.gamification.skilltree.createDefaultSkillList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities=[TaskEntity::class, SkillEntity::class, RewardEntity::class, SkillEdge::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun skillDao() : SkillDao
    abstract fun rewardDao() : RewardDao
    abstract fun edgeDao() : SkillEdgeDao

    private class AppDBCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val skills = populateSkills(database.skillDao())
                    val tasks = populateTasks(database.taskDao(), skills)
                    val rewards = populateRewards(database.rewardDao(), tasks)
                    populateEdges(database.edgeDao(), skills)
                }
            }
        }

        suspend fun populateTasks(dao : TaskDao, skills : ArrayList<Skill>) : ArrayList<Task>{
            // Delete all content here.
            dao.deleteAll()
            val dummy = createDefaultTaskList(skills)

            dummy.forEach {
                val t = getTaskEntity(it)
                dao.add(t)
            }
            return dummy
        }

        suspend fun populateSkills(dao : SkillDao) : ArrayList<Skill>{
            // Delete all content here.
            dao.deleteAll()
            val dummy = createDefaultSkillList()
            dummy.forEach {
                val t = getSkillEntity(it)
                dao.add(t)
            }
            return dummy
        }

        suspend fun populateRewards(dao : RewardDao, tasks : ArrayList<Task>) : ArrayList<Reward>{
            // Delete all content here.
            val rewards = ArrayList<Reward>()
            dao.deleteAll()
            tasks.forEach {task ->
                task.rewards.forEach {
                    dao.add(getTaskSkillReward(it))
                    rewards.add(it)
                }
            }
            return rewards
        }

        suspend fun populateEdges(dao : SkillEdgeDao, skills: ArrayList<Skill>) {
            dao.deleteAll()
            skills.forEach {skill ->
                skill.children.forEach {child ->
                    dao.add(SkillEdge(skill.id, child.id))
                }
            }
        }

    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(AppDBCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
