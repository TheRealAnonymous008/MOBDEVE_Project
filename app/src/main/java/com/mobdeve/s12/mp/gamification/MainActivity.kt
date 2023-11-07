package com.mobdeve.s12.mp.gamification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.generateDefaultProfile
import com.mobdeve.s12.mp.gamification.ui.components.LoginPage
import com.mobdeve.s12.mp.gamification.ui.components.MainWindow
import com.mobdeve.s12.mp.gamification.ui.components.Register


class MainActivity : AppCompatActivity() {
    companion object {
        const val PROFILE_DATA_KEY = "PROFILE_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val profile: Profile = generateDefaultProfile()
        setContent {
            MainWindow(profile = profile)
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
}