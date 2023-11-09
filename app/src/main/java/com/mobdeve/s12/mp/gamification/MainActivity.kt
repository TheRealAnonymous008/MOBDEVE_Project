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
import com.mobdeve.s12.mp.gamification.localdb.TaskRepository
import com.mobdeve.s12.mp.gamification.localdb.TaskViewModel
import com.mobdeve.s12.mp.gamification.localdb.TaskViewModelFactory
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

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as MainApplication).taskRepository)
    }

    val profileState = mutableStateOf(generateDefaultProfile())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        taskViewModel.allTasks.observe(this) {tasks ->
            tasks?.let {
                val profile = profileState.value
                for (task in tasks) {
                    profile.tasks.add(getTaskFromEntity(task))
                }
                profileState.value = profile
                
                setContent {
                    MainWindow(profile = profileState.value)
                }
            }
        }

        setContent {
            MainWindow(profile = profileState.value)
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