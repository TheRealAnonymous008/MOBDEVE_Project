package com.mobdeve.s12.saquilayan.ethanjared.skilltreedemo.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobdeve.s12.mp.gamification.model.Skill
import com.mobdeve.s12.mp.gamification.shapes.SkillNodeShape
import com.mobdeve.s12.mp.gamification.skilltree.SkillNode

import org.w3c.dom.Text

@Composable
fun skillBox(skillNode: SkillNode) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    shadowElevation = 8.dp.toPx()
                    shape = SkillNodeShape(24.dp.toPx())
                    clip = true
                }
                .background(color = Color.Red)
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

