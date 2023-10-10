package com.mobdeve.s12.mp.gamification.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.modifiers.advancedShadow
import com.mobdeve.s12.mp.gamification.model.ProfileDetails
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.Task
import com.mobdeve.s12.mp.gamification.ui.theme.Background
import com.mobdeve.s12.mp.gamification.ui.theme.MOBDEVEProjectTheme
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainWindow(profile : Profile) {
    val currentWindow = 0

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
                        .height(550.dp)

                ) { Box(
                    modifier = Modifier
                        .background(SecondaryColor)
                        .padding(10.dp),
                ){


                    HorizontalPager(3) {
                        when(it) {
                            0 -> TaskList(taskList = profile.tasks) /* TODO: Replace this with the calendar */
                            1 -> TaskList(taskList = profile.tasks)
                            2 -> SkillList (skillList = profile.skills)
                        }
                    }
                }}

            }
        }
    }
}

