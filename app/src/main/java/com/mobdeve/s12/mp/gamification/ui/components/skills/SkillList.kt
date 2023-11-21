package com.mobdeve.s12.mp.gamification.ui.components.skills

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.SkillListHolder
import com.mobdeve.s12.mp.gamification.model.createEmptySkill
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SkillList(skillList : SkillListHolder, profile : Profile, repo : RepositoryHolder){
    val scope = CoroutineScope(Dispatchers.Main)

    var skillListState = remember { mutableStateListOf(*skillList.skills.toTypedArray()) }

    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)

        ) {
            items(skillList.getSorted()) { skill ->
                SkillEntry(skill, profile,
                    onUpdate = {
                        scope.launch(Dispatchers.IO) {
                            repo.skillRepository.update(it)
                        }
                    },
                    onDelete = {
                        skillListState.remove(it)
                        scope.launch(Dispatchers.IO) {
                            repo.skillRepository.delete(it.id)
                            repo.rewardRepository.deleteWithSkill(it.id)
                        }
                    }, repo)
                }
            }

        var isShowingTaskDetails = remember { mutableStateOf(false) }
        var skillInserted = remember { mutableStateOf<Skill?>(null)}

        SkillDialog(
            isVisible = isShowingTaskDetails,
            skill = skillInserted.value,
            profile = profile,
            onUpdate = {
                scope.launch(Dispatchers.IO) {
                    val id = repo.skillRepository.add(it)
                    it.id = id
                    skillList.add(it)
                    skillListState.add(it)
                }
            },
            onDelete = {
                skillListState.remove(it)
                scope.launch(Dispatchers.IO) {
                    repo.skillRepository.delete(it.id)
                    repo.rewardRepository.deleteWithSkill(it.id)
                }
            }, repo
        )


        LargeFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomStart),
            onClick = {
                skillInserted.value = createEmptySkill()
                isShowingTaskDetails.value = true
            },
            containerColor = AccentColor,
            contentColor = Color.Black,
            shape = CircleShape
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Add FAB",
                modifier = Modifier.size(80.dp, 80.dp)
            )
        }

    }
}


