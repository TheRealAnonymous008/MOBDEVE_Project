package com.mobdeve.s12.mp.gamification.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobdeve.s12.mp.gamification.MainActivity.Routes.SKILLTREE_WINDOW
import com.mobdeve.s12.mp.gamification.R
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.model.Cosmetic
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.modifiers.advancedShadow
import com.mobdeve.s12.mp.gamification.ui.components.calendar.TaskSchedule
import com.mobdeve.s12.mp.gamification.ui.components.cosmetics.ShopWindow
import com.mobdeve.s12.mp.gamification.ui.components.skills.SkillList
import com.mobdeve.s12.mp.gamification.ui.components.tasks.TaskList
import com.mobdeve.s12.mp.gamification.ui.theme.Background
import com.mobdeve.s12.mp.gamification.ui.theme.MOBDEVEProjectTheme
import com.mobdeve.s12.mp.gamification.ui.theme.OtherAccent
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


enum class HorDir(val direction : Int) {
    LEFT(-1),
    RIGHT(1)
}

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainWindow(profile : Profile, cosmetics: ArrayList<Cosmetic>, repo: RepositoryHolder, navController: NavController) {
    val scope = rememberCoroutineScope()
    val horizontalPagerState = rememberPagerState(
        initialPage = 1,
        initialPageOffsetFraction = 0f
    ) { 3 }
    val isShopVisible = remember { mutableStateOf(false) }
    val isTaskVisible = remember {mutableStateOf(true)}

    val isLeftScrollVisible = remember{ mutableStateOf(true) }
    val isRightScrollVisible = remember{ mutableStateOf(true) }

    var profileState by remember {mutableStateOf(profile)}
    var currency = remember {mutableIntStateOf(profileState.profileDetails.currency)}

    fun scrollPage(pageOffset : Int) {
        scope.launch {
            horizontalPagerState.animateScrollToPage(horizontalPagerState.currentPage + pageOffset)
        }
    }

    MOBDEVEProjectTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                AnimatedVisibility(visible = isTaskVisible.value) {
                    Column {
                        ProfileHeader(profileState.profileDetails, navController)
                        Card(
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

                        ) {
                            Box(
                                modifier = Modifier
                                    .background(SecondaryColor)
                                    .padding(10.dp),
                            ) {
                                HorizontalPager(
                                    state = horizontalPagerState,
                                    beyondBoundsPageCount = 1

                                ) { page ->
                                    // Our page content
                                    when (page) {
                                        0 -> TaskSchedule(taskList = profileState.tasks)
                                        1 -> TaskList(
                                            taskList = profileState.tasks,
                                            profile = profileState,
                                            onProfileUpdate = {
                                                profileState = it
                                                currency.value =
                                                    profileState.profileDetails.currency
                                            },
                                            repo = repo
                                        )

                                        2 -> SkillList(
                                            skillList = profileState.skills,
                                            profile = profileState,
                                            repo = repo
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
                Row(
                    modifier = Modifier
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    if (horizontalPagerState.currentPage != 0) {
                        Button(
                            onClick = {
                                MainScope().launch { scrollPage(HorDir.LEFT.direction) }
                            },
                            colors = ButtonDefaults.buttonColors(OtherAccent),
                            shape = CircleShape,
                            modifier = Modifier
                                .size(50.dp)
                                .fillMaxHeight()
                                .weight(0.7F)
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
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "settings button",
                                tint = Color.White
                            )
                        }
                    }
                    Button(
                        onClick = { navController.navigate(SKILLTREE_WINDOW) },
                        colors = ButtonDefaults.buttonColors(OtherAccent),
                        shape = CircleShape,
                        modifier = Modifier
                            .size(50.dp)
                            .fillMaxHeight()
                            .weight(0.7F)
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
                        Icon(
                            painterResource(id = R.drawable.tree3),
                            contentDescription = "settings button",
                            tint = Color.White
                        )
                    }
                    Button(
                        onClick = {
                            isTaskVisible.value = !isTaskVisible.value
                            isShopVisible.value = !isShopVisible.value
                        },
                        colors = ButtonDefaults.buttonColors(OtherAccent),
                        modifier = Modifier
                            .size(50.dp)
                            .fillMaxHeight()
                            .weight(2.8F)
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
                    if (horizontalPagerState.currentPage != 2) {
                        Button(
                            onClick = {
                                MainScope().launch { scrollPage(HorDir.RIGHT.direction) }
                            },
                            colors = ButtonDefaults.buttonColors(OtherAccent),
                            shape = CircleShape,
                            modifier = Modifier
                                .size(50.dp)
                                .fillMaxHeight()
                                .weight(0.7F)
                                .advancedShadow(
                                    Color.Black,
                                    offsetX = 10.dp,
                                    offsetY = 5.dp,
                                    spread = 4.dp,
                                    blurRadius = 10.dp,
                                    borderRadius = 50.dp
                                ),
                            contentPadding = PaddingValues(0.dp),
                        )
                        {
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = "settings button",
                                tint = Color.White
                            )
                        }
                    }

//                    key (profileState){
//                        Card(
//                            colors = CardDefaults.cardColors(Color.Transparent),
//                            modifier = Modifier
//                                .size(40.dp)
//                                .fillMaxHeight()
//                                .fillMaxWidth()
//                                .padding(25.dp, 0.dp)
//                                .weight(1F),
//                        )
//                        {
//                            Text(
//                                text = "${currency.value}",
//                                modifier = Modifier.fillMaxWidth(),
//                                color = Color.White
//                            )
//                            Icon(
//                                Icons.Default.Star,
//                                contentDescription = "currency indicator",
//                                tint = Color.Yellow
//                            )
//                        }
//                    }


                }
                AnimatedVisibility(
                    visible = isShopVisible.value,
                    enter = slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight }
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { fullHeight -> fullHeight }
                    )
                ) {
                    ShopWindow(
                        profile = profileState,
                        onProfileUpdate = {
                            profileState = it
                            currency.value = profileState.profileDetails.currency
                            MainScope().launch {
                                it.cosmetics.cosmetics.forEach {cosmetic ->
                                    if(!cosmetic.owned) {
                                        cosmetic.setOwned()
                                        repo.cosmeticRepository.update(cosmetic)
                                    }
                                }
                            }
                        },
                        cosmeticsList = cosmetics,
                        modifier = Modifier.fillMaxHeight(),
                    )
                }
            }
        }
    }
}
