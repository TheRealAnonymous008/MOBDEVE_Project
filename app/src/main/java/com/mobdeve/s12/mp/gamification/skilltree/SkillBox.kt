package com.mobdeve.s12.saquilayan.ethanjared.skilltreedemo.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.modifiers.advancedShadow
import com.mobdeve.s12.mp.gamification.skilltree.SkillNodeShape
import com.mobdeve.s12.mp.gamification.skilltree.SkillNode
import com.mobdeve.s12.mp.gamification.ui.components.skills.getPriorityColor

@Composable
fun skillBox(skillNode: SkillNode) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
    ) {
        var bgcolor = getPriorityColor(skillNode.skill.priority)
        Box(
            modifier = Modifier
                .advancedShadow(
                    Color.Black,
                    offsetX = 0.dp,
                    offsetY = 0.dp,
                    spread = 1.dp,
                    blurRadius = 25.dp,
                    borderRadius = 50.dp
                )
                .graphicsLayer {
                    shadowElevation = 8.dp.toPx()
                    shape = SkillNodeShape(24.dp.toPx())
                    clip = true
                }
                .background(color = bgcolor)
                .size(50.dp)
        )


        Text(
            text = skillNode.skill.name,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 12.sp
        )
    }

}
@Preview
@Composable
fun skillBoxPreview() {
    var nodes = SkillNode(
        Skill(
            1, "Multivariable Calculus"
        ),
        1,
        1,
        1
    )

    skillBox(skillNode = nodes)
}

