package com.mobdeve.s12.mp.gamification.ui.components.tasks

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.model.Reward
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@Composable
fun TaskRewardsList(rewards: ArrayList<Reward>){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),

        // TODO: Add padding as necessary

    ) {
        items(rewards) { reward ->
            RewardEntry(reward)
        }
    }
}

@Composable
fun RewardEntry(reward : Reward) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        // Skill Name
        Text(
            text = reward.skill.name,
            modifier = Modifier
                .weight(1f),
            color = TextColor,
        )

        // XP
        Text(
            text = reward.xp.toString(),
            modifier = Modifier
                .weight(0.25f),
            color = TextColor,
        )
    }
}