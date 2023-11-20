package com.mobdeve.s12.mp.gamification

import android.app.Application
import android.content.res.Resources
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.mobdeve.s12.mp.gamification.localdb.AppDatabase
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.localdb.SkillRepository
import com.mobdeve.s12.mp.gamification.localdb.TaskRepository
import com.mobdeve.s12.mp.gamification.model.Cosmetic
import com.mobdeve.s12.mp.gamification.model.FeetCosmetic
import com.mobdeve.s12.mp.gamification.model.HeadCosmetic
import com.mobdeve.s12.mp.gamification.model.LegsCosmetic
import com.mobdeve.s12.mp.gamification.model.TorsoCosmetic
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