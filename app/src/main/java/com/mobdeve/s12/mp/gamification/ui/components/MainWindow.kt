package com.mobdeve.s12.mp.gamification.ui.components

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.generateDefaultProfile
import com.mobdeve.s12.mp.gamification.modifiers.advancedShadow
import com.mobdeve.s12.mp.gamification.ui.components.skills.SkillList
import com.mobdeve.s12.mp.gamification.ui.components.tasks.TaskList
import com.mobdeve.s12.mp.gamification.ui.theme.Background
import com.mobdeve.s12.mp.gamification.ui.theme.MOBDEVEProjectTheme
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainWindow(profile : Profile) {
//    val pagerState = rememberPagerState(
//        initialPage = 1,
//        initialPageOffsetFraction = 0f
//    ) {
//        // provide pageCount
//        3
//    }

    MOBDEVEProjectTheme{
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                ProfileHeader(profile.profileDetails)
                Card (
                    modifier = Modifier
                        .padding(10.dp)
                        .advancedShadow(
                            Color.Black,
                            offsetX = 10.dp,
                            offsetY = 5.dp,
                            spread = 4.dp,
                            blurRadius = 5.dp,
                            borderRadius = 5.dp
                        )
                        .fillMaxHeight(0.90F)

                ) { Box(
                    modifier = Modifier
                        .background(SecondaryColor)
                        .padding(10.dp),
                ){

                /* TODO: Replace this with the calendar */
//                HorizontalPager(state = pagerState) { page ->
//                    // Our page content
//                    when(page) {
//                        0 -> TaskList(taskList = profile.tasks, profile = profile)
//                        1 -> TaskList(taskList = profile.tasks, profile = profile)
//                        2 -> SkillList(skillList = profile.skills, profile = profile )
//                    }
//                }
                    TaskList(taskList = profile.tasks, profile = profile)
                }}

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainWindow() {
    val profile : Profile = generateDefaultProfile()
    MainWindow(profile)
}
