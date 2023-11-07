package com.mobdeve.s12.mp.gamification

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import com.mobdeve.s12.mp.gamification.skilltree.SkillNode
import com.mobdeve.s12.mp.gamification.skilltree.SkillTreeLazyLayoutState
import com.mobdeve.s12.mp.gamification.skilltree.SkillTreeLazyListScope
import com.mobdeve.s12.mp.gamification.skilltree.rememberSkillTreeItemProvider

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SkillTreeLazyLayout(
    modifier: Modifier,
    state: SkillTreeLazyLayoutState,
    content: SkillTreeLazyListScope.() -> Unit
) {
    val itemProvider = rememberSkillTreeItemProvider(customLazyListScope = content)

    LazyLayout(
        modifier = modifier
            .clipToBounds()
            .lazyLayoutPointerInput(state),
        itemProvider = itemProvider,
    ) {constraints ->
        val boundaries = state.getBoundaries(constraints = constraints)
        val indexes = itemProvider.getItemIndexesInRange(boundaries)

        val indexesWithPlaceables = indexes.associateWith {
            measure(it, Constraints())
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            indexesWithPlaceables.forEach {(index, placeables) ->
                val item = itemProvider.getItem(index)
                item?.let {
                    placeItem(state, item, placeables)
                }
            }
        }
    }
}

// allows dragging
private fun Modifier.lazyLayoutPointerInput(state: SkillTreeLazyLayoutState): Modifier {
    return pointerInput(Unit) {
        detectDragGestures { change, dragAmount ->
            change.consume()
            state.onDrag(IntOffset(dragAmount.x.toInt(), dragAmount.y.toInt()))
        }
    }
}

// no idea how this works
private fun Placeable.PlacementScope.placeItem(state: SkillTreeLazyLayoutState, skillNode: SkillNode, placeables: List<Placeable>) {
    val xPosition = skillNode.xPos - state.offsetState.value.x
    val yPosition = skillNode.yPos - state.offsetState.value.y

    placeables.forEach { placeable ->
        placeable.placeRelative(
            xPosition,
            yPosition
        )
    }
}
