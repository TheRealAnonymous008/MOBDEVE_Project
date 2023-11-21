package com.mobdeve.s12.mp.gamification.ui.components.skills

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillEntry(skill : Skill, profile : Profile, onUpdate: (s : Skill) -> Unit, onDelete : (s : Skill) -> Unit, repo: RepositoryHolder){
    var isShowingTaskDetails = remember { mutableStateOf(false) }

    SkillDialog(
        isVisible = isShowingTaskDetails,
        skill = skill,
        profile = profile,
        onUpdate = onUpdate,
        onDelete = onDelete,
        repo = repo
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        onClick = {
            isShowingTaskDetails.value = true
        }

    ) {
        // Skill Title, Priority, and Image
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    var name = skill.name
                    var level = skill.level
                    if (name.isBlank())
                        name = "Untitled Skill"
                    Box(modifier = Modifier
                        .weight(0.5f)) {
                        Column (verticalArrangement = Arrangement.Center) {
                            Text(
                                text = name,
                                modifier = Modifier
                                    .padding(bottom = 4.dp),
                                color = TextColor,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Text(
                                text = "Lvl: $level",
                                modifier = Modifier
                                    .padding(bottom = 4.dp),
                                color = AccentColor,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(0.34f)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    ) {
                        PriorityIndicator(
                            modifier = Modifier.padding(end = 10.dp),
                            priority = skill.priority
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Progress Bar
                ProgressBar(min = 0f, max = 100f, value = skill.xp.toFloat() )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
    }
}


