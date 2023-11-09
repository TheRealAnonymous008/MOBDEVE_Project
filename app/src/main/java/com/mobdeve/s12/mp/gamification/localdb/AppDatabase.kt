package com.mobdeve.s12.mp.gamification.localdb

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mobdeve.s12.mp.gamification.model.createDefaultTaskList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TaskEntity::class, SkillEntity::class, TaskSkillRewardEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun skillDao() : SkillDao
    abstract fun taskSkillRewardDao() : TaskSkillRewardDao

    private class AppDBCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateTasks(database.taskDao())
                }
            }
        }

        suspend fun populateTasks(dao : TaskDao) {
            // Delete all content here.
            dao.deleteAll()
            val dummy = createDefaultTaskList()

            dummy.forEach {
                val t = getTaskEntity(it)
                dao.insert(t)
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
