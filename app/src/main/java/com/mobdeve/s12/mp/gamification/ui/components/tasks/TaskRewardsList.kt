package com.mobdeve.s12.mp.gamification.ui.components.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun TaskRewardsList(rewards: ArrayList<Reward> , profile : Profile){
    var rewardListState = remember { mutableStateListOf(*rewards.toTypedArray()) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            // TODO: Add padding as necessary

        ) {
            items(rewardListState) { reward ->
                RewardEntry(reward)
            }
        }

        AddReward(rewards, profile) {
            rewardListState.add(it)
        }
    }
}

@Composable
fun AddReward(rewards: ArrayList<Reward>, profile: Profile, onUpdate: (it: Reward) -> Unit){
    var selectedSkill: Skill? by remember { mutableStateOf(null) }
    var amount by remember { mutableStateOf("")}
    var expanded by remember { mutableStateOf(false) }

    Column(
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(SecondaryColor)
            ) {
                TextButton(onClick = { expanded = !expanded }) {
                    if (selectedSkill == null)
                        Text("Select Skill")
                    else
                        Text(selectedSkill!!.name)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    profile.skills.skills.forEach { skill ->
                        DropdownMenuItem(
                            onClick = {
                                selectedSkill = skill
                                expanded = false
                            },
                            text = {
                                Text(skill.name)
                            }
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(SecondaryColor)
            ) {
                BasicTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        Box() {
                            if (amount.isEmpty()) {
                                Text(
                                    text = "0",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Gray
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (selectedSkill != null) {
                    val r = Reward(selectedSkill!!, amount.toFloat())
                    rewards.add(r)
                    onUpdate(r)
                    amount = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("+", color = Color.White)
        }
    }
}

