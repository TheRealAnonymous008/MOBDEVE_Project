package com.mobdeve.s12.mp.gamification.model

import com.mobdeve.s12.mp.gamification.R
import com.mobdeve.s12.mp.gamification.skilltree.createDefaultSkillList
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

    return Profile(
        profileDetails,
        SkillListHolder(skillList),
        taskListHolder,
        ArrayList<Cosmetic>()
    )
}