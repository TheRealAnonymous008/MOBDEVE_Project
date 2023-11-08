package com.mobdeve.s12.mp.gamification.model

import com.mobdeve.s12.mp.gamification.R
import com.mobdeve.s12.mp.gamification.skilltree.createDefaultSkillList
import com.mobdeve.s12.mp.gamification.ui.components.skills.SkillList
import java.io.Serializable

class Profile (
    var profileDetails: ProfileDetails,
    var skills : SkillListHolder,
    var tasks: TaskListHolder,
    var cosmetics : ArrayList<Cosmetic>
) : Serializable {}

fun generateDefaultProfile() : Profile{
    val profileDetails = ProfileDetails("Hello",
        "Generic Description",
        R.drawable.download, currency = 10)

    val skillList = createDefaultSkillList()
    val taskList = createDefaultTaskList(skillList)

    for(skill in skillList){
        skill.imageId = R.drawable.download
    }

    val taskListHolder = TaskListHolder()
    for (task in taskList) {
        taskListHolder.add(task)
    }

    val skillListHolder = SkillListHolder()
    for (skill in skillList) {
        skillListHolder.add(skill)
    }

    return Profile(
        profileDetails,
        skillListHolder,
        taskListHolder,
        ArrayList<Cosmetic>()
    )
}