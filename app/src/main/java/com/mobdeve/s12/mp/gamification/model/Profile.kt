package com.mobdeve.s12.mp.gamification.model

import com.mobdeve.s12.mp.gamification.R

class Profile (
    var profileDetails: ProfileDetails,
    var skills : SkillListHolder,
    var tasks: TaskListHolder,
    var cosmetics : ArrayList<Cosmetic>
){}

fun generateDefaultProfile() : Profile{
    val profileDetails = ProfileDetails("Hello",
        "Generic Description",
        R.drawable.download, currency = 10)

    val skillList = createDefaultSkillList()
    val taskList = createDefaultTaskList(skillList)

    for(skill in skillList){
        skill.imageId = R.drawable.download
    }

    return Profile(
        profileDetails,
        SkillListHolder(skillList),
        TaskListHolder(taskList),
        ArrayList<Cosmetic>()
    )
}