package com.mobdeve.s12.mp.gamification.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.localdb.AppDatabase
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.createDefaultCosmeticList
import com.mobdeve.s12.mp.gamification.model.generateDefaultProfile
import com.mobdeve.s12.mp.gamification.modifiers.advancedShadow
import com.mobdeve.s12.mp.gamification.ui.components.calendar.TaskSchedule
import com.mobdeve.s12.mp.gamification.ui.components.cosmetics.ShopWindow
import com.mobdeve.s12.mp.gamification.ui.components.skills.SkillList
import com.mobdeve.s12.mp.gamification.ui.components.skilltree.SkillTreeWindow
import com.mobdeve.s12.mp.gamification.ui.components.tasks.TaskList
import com.mobdeve.s12.mp.gamification.ui.theme.Background
import com.mobdeve.s12.mp.gamification.ui.theme.MOBDEVEProjectTheme
import com.mobdeve.s12.mp.gamification.ui.theme.OtherAccent
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor


@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun MainWindow(profile : Profile, db : AppDatabase) {
    val horizontalPagerState = rememberPagerState(
        initialPage = 2,
        initialPageOffsetFraction = 0f
    ) { 4 }

    val cosmeticList = createDefaultCosmeticList()
    val isShopVisible = remember { mutableStateOf(false) }
    val isTaskVisible = remember {mutableStateOf(true)}

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
                AnimatedVisibility(visible = isTaskVisible.value) {
                    Column {
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
                                .fillMaxHeight(0.85F)

                        ) { Box(
                            modifier = Modifier
                                .background(SecondaryColor)
                                .padding(10.dp),
                        ){
                            HorizontalPager(state = horizontalPagerState) { page ->
                                // Our page content
                                when(page) {
                                    0 -> TaskSchedule(taskList = profile.tasks)
                                    1 -> TaskList(taskList = profile.tasks, profile = profile, db = db)
                                    2 -> SkillList(skillList = profile.skills, profile = profile, db = db)
                                    3 -> SkillTreeWindow(skillList = profile.skills, profile = profile)
                                }
                            }
                        }}
                    }

                }
                Row (
                    modifier = Modifier
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ){
                    Button( onClick = { TODO("Settings Button") },
                        colors = ButtonDefaults.buttonColors(OtherAccent),
                        shape = CircleShape,
                        modifier = Modifier
                            .size(50.dp)
                            .fillMaxHeight()
                            .weight(0.5F)
                            .advancedShadow(
                                Color.Black,
                                offsetX = 10.dp,
                                offsetY = 5.dp,
                                spread = 4.dp,
                                blurRadius = 10.dp,
                                borderRadius = 50.dp
                            ),
                        contentPadding = PaddingValues(0.dp)
                    )
                    {
                        Icon(Icons.Default.Settings, contentDescription = "settings button", tint = Color.White)
                    }
                    Button( onClick = {
                        isTaskVisible.value = !isTaskVisible.value
                        isShopVisible.value = !isShopVisible.value
                    },
                        colors = ButtonDefaults.buttonColors(OtherAccent),
                        modifier = Modifier
                            .size(50.dp)
                            .fillMaxHeight()
                            .weight(2F)
                            .advancedShadow(
                                Color.Black,
                                offsetX = 10.dp,
                                offsetY = 5.dp,
                                spread = 4.dp,
                                blurRadius = 10.dp,
                                borderRadius = 50.dp
                            ),
                        contentPadding = PaddingValues(0.dp)
                    )
                    {
                        Text(
                            text = "MARKET",
                            letterSpacing = 10.sp,
                            fontSize = 20.sp
                        )
                    }
                    Button( onClick = { TODO("Currency Image") },
                        colors = ButtonDefaults.buttonColors(OtherAccent),
                        shape = CircleShape,
                        modifier = Modifier
                            .size(50.dp)
                            .fillMaxHeight()
                            .weight(0.75F)
                            .advancedShadow(
                                Color.Black,
                                offsetX = 10.dp,
                                offsetY = 5.dp,
                                spread = 4.dp,
                                blurRadius = 10.dp,
                                borderRadius = 50.dp
                            ),
                        contentPadding = PaddingValues(0.dp)
                    )
                    {
                        Icon(Icons.Default.Star, contentDescription = "settings button", tint = Color.White)
                    }

                }
                AnimatedVisibility(
                    visible = isShopVisible.value,
                    enter = slideInVertically(
                        initialOffsetY = {fullHeight -> fullHeight}
                    ) ,
                    exit = slideOutVertically(
                        targetOffsetY = {fullHeight -> fullHeight}
                    )
                ) {
                    ShopWindow(
                        profileDetails = profile.profileDetails,
                        cosmeticsList = cosmeticList,
                        modifier = Modifier.fillMaxHeight())
                }

            }
        }
    }
}
