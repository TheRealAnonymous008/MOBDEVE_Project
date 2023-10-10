package com.mobdeve.s12.mp.gamification.ui.components.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Reward
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun TaskRewardsList(rewards: ArrayList<Reward> , profile : Profile){
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            // TODO: Add padding as necessary

        ) {
            items(rewards) { reward ->
                RewardEntry(reward)
            }
        }

        AddReward(rewards, profile)
    }
}

@Composable
fun AddReward(rewards: ArrayList<Reward>, profile: Profile){
    var skill by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("")}

    Column() {
        Row() {
            BasicTextField(
                value = skill,
                onValueChange = {
                    skill= it
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = TextColor,
                ),
                modifier = Modifier
                    .background(Color.Transparent),
                decorationBox = { innerTextField ->
                        Box() {
                            if (skill.isEmpty()) {
                                Text(
                                    text = "Enter skill id here",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.LightGray
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            BasicTextField(
                value = amount,
                onValueChange = {
                    skill= it
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = TextColor,
                ),
                modifier = Modifier
                    .background(Color.Transparent),
                decorationBox = { innerTextField ->
                    Box() {
                        if (skill.isEmpty()) {
                            Text(
                                text = "Enter skill id here",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.LightGray
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }

        Button(
            onClick = {
                val sk = profile.skills.get(skill)
                if (sk != null) {
                    rewards.add(Reward(sk, amount.toFloat()))
                }
            }
        ) {
            Text("+")
        }
    }
}
