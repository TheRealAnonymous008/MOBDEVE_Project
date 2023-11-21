package com.mobdeve.s12.mp.gamification.ui.components.tasks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.Dialog
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Task

@Composable
fun TaskDialog(
    isVisible: MutableState<Boolean>,
    task : Task?,
    profile : Profile,
    onDelete : (t : Task) -> Unit,
    onUpdate : (t : Task) -> Unit,
    repo : RepositoryHolder)
{
    if (isVisible.value && task != null) {
        Dialog(
            onDismissRequest = {
                isVisible.value = false
                onUpdate(task)
            },

        )
        {
            TaskDetailsLayout(task = task, profile = profile,
                onDelete = {
                    isVisible.value = false
                    profile.tasks.remove(task)
                    onDelete(task)
                },
                repo = repo
            )
        }
    }
}