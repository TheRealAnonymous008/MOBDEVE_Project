package com.mobdeve.s12.mp.gamification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import com.mobdeve.s12.mp.gamification.localdb.AppDatabase
import com.mobdeve.s12.mp.gamification.localdb.SkillViewModel
import com.mobdeve.s12.mp.gamification.localdb.SkillViewModelFactory
import com.mobdeve.s12.mp.gamification.localdb.TaskRepository
import com.mobdeve.s12.mp.gamification.localdb.TaskViewModel
import com.mobdeve.s12.mp.gamification.localdb.TaskViewModelFactory
import com.mobdeve.s12.mp.gamification.localdb.getSkillFromEntity
import com.mobdeve.s12.mp.gamification.localdb.getTaskFromEntity
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.generateDefaultProfile
import com.mobdeve.s12.mp.gamification.ui.components.LoginPage
import com.mobdeve.s12.mp.gamification.ui.components.MainWindow
import com.mobdeve.s12.mp.gamification.ui.components.Register
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1

    private var database : AppDatabase? = null

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as MainApplication).taskRepository)
    }

    private val skillViewModel : SkillViewModel by viewModels {
        SkillViewModelFactory((application as MainApplication).skillRepository)
    }

    val profileState = mutableStateOf(generateDefaultProfile())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = ((application as MainApplication)).database

        fetchTasks()
        fetchSkills()

        update()
    }

    private fun fetchTasks() {
        taskViewModel.allTasks.observe(this) { tasks ->
            tasks?.let {
                val profile = profileState.value
                profile.tasks.clear()
                for (task in tasks) {
                    profile.tasks.add(getTaskFromEntity(task))
                }
                profileState.value = profile
                update()
            }
        }
    }


    private fun fetchSkills() {
        skillViewModel.allSkills.observe(this) { skills ->
            skills?.let {
                val profile = profileState.value
                profile.skills.clear()
                for (skill in skills) {
                    profile.skills.add(getSkillFromEntity(skill))
                }
                profileState.value = profile
                update()
            }
        }
    }

    private fun update() {
        if (database === null) {
            database = ((application as MainApplication)).database
        }
        setContent {
            MainWindow(profile = profileState.value, db = database!!)
        }
    }
}

/*@Composable
fun navigatePage() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login_page",
        builder = {
            composable("login_page", content = { LoginPage(navController = navController) })
            composable("register_page", content = { Register(navController = navController) })
        }
    )
}*/