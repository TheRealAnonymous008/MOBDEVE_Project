package com.mobdeve.s12.mp.gamification.ui.components.skills

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.Dialog
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Skill

@Composable
fun SKillDialog(
    isVisible : MutableState<Boolean>,
    skill : Skill?,
    profile : Profile,
    onUpdate: (s : Skill) -> Unit,
    onDelete : (s : Skill) -> Unit) {

    if (isVisible.value && skill != null) {
        Dialog(
            onDismissRequest = {
                isVisible.value = false
                onUpdate(skill)
            }) {
            SkillDetailsLaoyut(skill = skill , profile = profile, onDelete = {
                isVisible.value = false
                profile.skills.remove(skill)
                onDelete(skill)
            })
        }
    }
}