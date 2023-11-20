package com.mobdeve.s12.mp.gamification

import android.app.Application
import android.util.Log
import com.mobdeve.s12.mp.gamification.localdb.AppDatabase
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.localdb.SkillRepository
import com.mobdeve.s12.mp.gamification.localdb.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.io.File

class MainApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())
    lateinit var repositoryHolder: RepositoryHolder

    override fun onCreate() {
        super.onCreate()
        repositoryHolder = RepositoryHolder(this, applicationScope)
    }
}