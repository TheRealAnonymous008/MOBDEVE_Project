package com.mobdeve.s12.mp.gamification.model

import com.mobdeve.s12.mp.gamification.R
import com.mobdeve.s12.mp.gamification.skilltree.createDefaultSkillList
import com.mobdeve.s12.mp.gamification.ui.components.skills.SkillList
import java.io.Serializable

class Profile (
    var profileDetails: ProfileDetails,
    var skills : SkillListHolder,
    var tasks: TaskListHolder,
    var cosmetics : CosmeticHolder,
) : Serializable {}

fun generateDefaultProfile() : Profile{
    val profileDetails = ProfileDetails("Hello",
        "Generic Description",
        Avatar(), currency = 10)
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