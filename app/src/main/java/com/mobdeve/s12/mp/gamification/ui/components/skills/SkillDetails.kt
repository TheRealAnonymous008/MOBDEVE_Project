package com.mobdeve.s12.mp.gamification.ui.components.skills

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.localdb.RepositoryHolder
import com.mobdeve.s12.mp.gamification.localdb.SkillEdge
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.Reward
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.model.SkillPriority
import com.mobdeve.s12.mp.gamification.ui.theme.AccentColor
import com.mobdeve.s12.mp.gamification.ui.theme.OtherAccent
import com.mobdeve.s12.mp.gamification.ui.theme.PrimaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor
import com.mobdeve.s12.mp.gamification.ui.theme.TextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SkillDetailsLayout(skill : Skill, profile : Profile, onDelete : () -> Unit, repo: RepositoryHolder) {
    var title by remember { mutableStateOf(skill.name) }
    var description by remember { mutableStateOf(skill.description) }
    var priorityValue by remember { mutableStateOf(skill.priority) }
    val priorities = SkillPriority.values().map { it.name }

    Column(
        modifier = Modifier
            .background(SecondaryColor)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Title of the Skill
        BasicTextField(
            value = title,
            cursorBrush = SolidColor(Color.White),
            onValueChange = {
                skill.name = it
                title = it
            },
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor,
            ),
            modifier = Modifier
                .background(PrimaryColor)
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                ) {
                    if (title.isEmpty()) {
                        Text(
                            text = "Untitled Skill",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        BasicTextField(
            value = description,
            cursorBrush = SolidColor(Color.White),
            onValueChange = {
                description= it
                skill.description = it
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = TextColor,
            ),
            modifier = Modifier
                .background(PrimaryColor)
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                ) {
                    if (description.isEmpty()) {
                        Text(
                            text = "Blank Description",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            }

        )

        Spacer(modifier = Modifier.height(16.dp))

        // Priority
        Box(modifier = Modifier.height(25.dp)) {
            var expanded by remember { mutableStateOf(false) }
            PriorityIndicator(
                priority = priorityValue,
                modifier = Modifier
                    .clickable {
                        expanded = true
                    }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(SecondaryColor)
                    .fillMaxWidth()
            ) {
                priorities.forEach { priority ->
                    DropdownMenuItem(
                        text = {
                            PriorityIndicator(priority = SkillPriority.valueOf(priority) )
                        },
                        onClick = {
                            priorityValue = SkillPriority.valueOf(priority)
                            expanded = false
                            skill.priority = SkillPriority.valueOf(priority)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        ChildrenList(parent = skill, profile = profile, repo = repo)


        // Delete Button
        Button(
            onClick = {
                onDelete()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text("Delete Skill", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ChildrenList(parent: Skill, profile: Profile, repo: RepositoryHolder) {
    val scope = CoroutineScope(Dispatchers.Main)
    var childrenList = remember {mutableListOf(*parent.children.toTypedArray())}
    var parentsList = remember {mutableListOf(*parent.getParents().toTypedArray())}
    var validSelection : MutableList<Skill> = remember {
        mutableListOf(*filterValid(childrenList.toList(), parentsList.toList(), profile.skills.skills).toTypedArray())
    }

    Text(
        text = "Children",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = TextColor,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .background(OtherAccent)
            .fillMaxWidth()

    )
    if(childrenList.size != 0)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(childrenList) {child ->
                Text(
                    text = child.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = TextColor
                )
            }
        }
    else
        Text("No Children", modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)
    AddSkillChildren(parent = parent, selection = validSelection.toList()) { child ->
        childrenList.add(child)
        scope.launch {
            repo.edgeRepository.add(parent, child)
        }
        parent.addChild(child)
    }


    Log.d("Valid Selection", validSelection.toString())
}

@Composable
fun AddSkillChildren(
    parent: Skill,
    selection: List<Skill>,
    onUpdate: (child: Skill) -> Unit) {
    var selectedChild: Skill? by remember { mutableStateOf(null) }
    var expanded by remember {mutableStateOf(false)}


    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
        Modifier
            .fillMaxWidth()
            .background(PrimaryColor)) {
        selection.forEach {skill ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = skill.name,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    ) },
                onClick = {
                    selectedChild = skill
                    expanded = false
                })
        }
    }
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box (Modifier.weight(0.7f)){
            TextButton(onClick = {expanded = !expanded}, Modifier.fillMaxWidth().background(Color.Transparent),
                shape = RoundedCornerShape(20.dp)) {
                if(selectedChild == null)
                    Text("Select Child", textAlign = TextAlign.Center, fontSize = 18.sp)
                else
                    Text(selectedChild!!.name, textAlign = TextAlign.Center, color = Color.White, fontSize = 18.sp, overflow = TextOverflow.Ellipsis)
            }

        }
        Box (Modifier.weight(0.2f)) {
            Button(
                onClick = {
                    if (selectedChild != null) {
                        parent.addChild(selectedChild!!)
                        onUpdate(selectedChild!!)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("+", color = Color.White)
            }
        }

    }


}

fun filterValid(children : List<Skill>, parents : List<Skill>, profileSkills : List<Skill>) : ArrayList<Skill> {
    var result : ArrayList<Skill> = ArrayList()

    profileSkills.forEach {skill ->
        if(skill !in children && skill !in parents && skill.parent == null) {
            result.add(skill)
        }
    }
    return result
}