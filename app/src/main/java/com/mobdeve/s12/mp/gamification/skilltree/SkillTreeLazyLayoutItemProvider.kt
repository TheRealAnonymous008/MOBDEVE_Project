package com.mobdeve.s12.mp.gamification.skilltree

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberSkillTreeItemProvider(customLazyListScope: SkillTreeLazyListScope.() -> Unit): SkillTreeLazyLayoutItemProvider {
    val customLazyListScopeState = remember {mutableStateOf(customLazyListScope)}.apply {
        value = customLazyListScope
    }

    return remember {
        SkillTreeLazyLayoutItemProvider(
            derivedStateOf {
                val layoutScope = SkillTreeLazyListScopeImpl().apply(customLazyListScopeState.value)
                layoutScope.nodes
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
class SkillTreeLazyLayoutItemProvider(
    private val itemsState: State<List<SkillTreeItemContent>>
) : LazyLayoutItemProvider {
    override val itemCount: Int
        get() = itemsState.value.size

    @Composable
    override fun Item(index: Int, key: Any) {
        val item = itemsState.value.getOrNull(index)
        item?.skillNodeContent?.invoke(item.skillNode)
    }

    fun getItemIndexesInRange(boundaries: ViewBoundaries): List<Int> {
        val result = mutableListOf<Int>()

        itemsState.value.forEachIndexed{ index, content ->
            val listItem = content.skillNode
            if(listItem.xPos in boundaries.fromX .. boundaries.toX &&
               listItem.yPos in boundaries.fromY .. boundaries.toY
                ) {
                result.add(index)
            }

        }
        return result
    }

    fun getItem(index: Int): SkillNode? {
        return itemsState.value.getOrNull(index)?.skillNode
    }
}