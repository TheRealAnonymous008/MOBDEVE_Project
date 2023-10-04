package com.mobdeve.s12.mp.gamification

import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.model.TimeInfo
import com.mobdeve.s12.mp.gamification.model.createDefaultTask
import com.mobdeve.s12.mp.gamification.ui.components.MainWindow
import java.time.LocalDateTime

class MainWindowCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainWindow() {
    val profile = Profile("Hello",
        "Generic Description",
        R.drawable.download)

    val taskList = ArrayList<Task>()
    taskList.add(createDefaultTask())

    MainWindow(profile, taskList)
}