package com.mobdeve.s12.mp.gamification.model

import com.mobdeve.s12.mp.gamification.R
import com.mobdeve.s12.mp.gamification.skilltree.createDefaultSkillList
import com.mobdeve.s12.mp.gamification.ui.components.skills.SkillList
import java.io.Serializable

class Profile (
    var profileDetails: ProfileDetails,
    var skills : SkillListHolder,
    var tasks: TaskListHolder,
    var cosmetics : ArrayList<Cosmetic>,
    var avatar: Avatar
) : Serializable {}

fun generateDefaultProfile() : Profile{
    val profileDetails = ProfileDetails("Hello",
        "Generic Description",
        Avatar(), currency = 10)

    val taskListHolder = TaskListHolder()
    val skillListHolder = SkillListHolder()
    var avatar = Avatar()
    return Profile(
        profileDetails,
        skillListHolder,
        taskListHolder,
        ArrayList<Cosmetic>(),
        avatar,
    )
}