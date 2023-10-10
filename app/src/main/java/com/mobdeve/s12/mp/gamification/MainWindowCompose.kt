package com.mobdeve.s12.mp.gamification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mobdeve.s12.mp.gamification.model.ProfileDetails
import com.mobdeve.s12.mp.gamification.model.createDefaultSkillList
import com.mobdeve.s12.mp.gamification.model.createDefaultTaskList
import com.mobdeve.s12.mp.gamification.ui.components.MainWindow

class MainWindowCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainWindow() {
    val profileDetails = ProfileDetails("Hello",
        "Generic Description",
        R.drawable.download, currency = 10)

    val taskList = createDefaultTaskList()
    val skillList = createDefaultSkillList()

    for(skill in skillList){
        skill.imageId = R.drawable.download
    }

    MainWindow(profileDetails, taskList, skillList)
}