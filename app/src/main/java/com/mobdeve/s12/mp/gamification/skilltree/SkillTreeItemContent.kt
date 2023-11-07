package com.mobdeve.s12.mp.gamification.skilltree
import androidx.compose.runtime.Composable

typealias ComposableNodeContent = @Composable (SkillNode) -> Unit

data class SkillTreeItemContent(
    val skillNode: SkillNode,
    val skillNodeContent: ComposableNodeContent
)