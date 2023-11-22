package com.mobdeve.s12.mp.gamification.model

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import java.io.Serializable

class Profile (
    var profileDetails: ProfileDetails,
    var skills : SkillListHolder,
    var tasks: TaskListHolder,
    var cosmetics : CosmeticHolder,
) : Serializable

fun generateDefaultProfile(context: Context) : Profile {
    val profileDetails = ProfileViewModel(context).profileDetails.value
    val cosmetics = CosmeticHolder()
    val taskListHolder = TaskListHolder()
    val skillListHolder = SkillListHolder()
    return Profile(
        profileDetails,
        skillListHolder,
        taskListHolder,
        cosmetics,
    )
}