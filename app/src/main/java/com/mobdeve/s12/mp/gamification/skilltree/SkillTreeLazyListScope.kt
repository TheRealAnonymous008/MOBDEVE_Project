package com.mobdeve.s12.mp.gamification.skilltree

interface SkillTreeLazyListScope {
    fun nodes(skillNodes: List<SkillNode>, skillContent: ComposableNodeContent)
}

class SkillTreeLazyListScopeImpl : SkillTreeLazyListScope {
    private val _nodes = mutableListOf<SkillTreeItemContent>()
    val nodes: List<SkillTreeItemContent> = _nodes

    override fun nodes(skillNodes: List<SkillNode>, skillContent: ComposableNodeContent) {
        skillNodes.forEach {_nodes.add(SkillTreeItemContent(it, skillNodeContent = skillContent))}
    }
}