package com.mobdeve.s12.mp.gamification.shapes

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class SkillNodeShape(private val cornerRadius: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            // Draw your custom path here
            path = drawSkillNodePath(size = size, cornerRadius = cornerRadius)
        )
    }
}

fun drawSkillNodePath(size: Size, cornerRadius: Float) : Path {
    return Path().apply {
        reset()
        val centerX = size.width / 2
        val centerY = size.height / 2

        moveTo(centerX, 0f)
        lineTo(size.width, centerY)
        lineTo(centerX, size.height)
        lineTo(0f, centerY)
        lineTo(centerX, 0f)
        close()
    }
}



