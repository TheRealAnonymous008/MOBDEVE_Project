package com.mobdeve.s12.mp.gamification.skilltree

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobdeve.s12.mp.gamification.model.Skill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.LinkedList


const val PARENT_X = 20
const val X_DISTANCE = 400
const val Y_DISTANCE = 400
const val INIT_HEIGHT = 300
const val SPACE_BETWEEN_PARENTS = 3

data class PositionHelper(
    var numNodes : ArrayList<Int>,
    var breadth : Int
) {
    override fun toString(): String {
        return "Number of nodes = $numNodes; MaxBreadth = $breadth"
    }
}

data class ScreenHeight(
    var minHeight: Int,
    var maxHeight: Int
)

// What this class is supposed to do is generate the items, but for this implementation, we don't need to update states or generate new items
// so it will be used to map skills to nodes
class SkillViewModel: ViewModel(), Actions {
    val state = MutableStateFlow(State())
    // store latest job (idk why we do this) they're like async calls
    private var skillViewGenerationJob: Job? = null
        set(value) {
            field?.cancel()
            field = value
        }

    init {
        val skills = createDefaultSkillList()
        generateNodes(skills)
    }

    private fun generateNodes(skills : List<Skill>) {
        skillViewGenerationJob = viewModelScope.launch (Dispatchers.Default) {
            var screenHeight = mutableStateOf( ScreenHeight(
                minHeight = INIT_HEIGHT,
                maxHeight = Y_DISTANCE
            )
            )
            val resultNodes = ArrayList<SkillNode>()
            val seenSkills = ArrayList<Skill>()
            // Naive solution, just refactor it later
            for ((parentsCount, skill) in skills.withIndex()) {
                var positionHelper = PositionHelper(
                    arrayListOf(1),
                    0
                )
                var currSkill = skill
                // make sure we're at parent
                while(currSkill.parent != null) {
                    currSkill = currSkill.parent!!
                    if(!seenSkills.contains(currSkill))
                        seenSkills.add(currSkill)
                }

                // Calculate parent position
                if(!seenSkills.contains(currSkill)) {
                    seenSkills.add(currSkill)
                    Log.d("RAN", "The node creation is run again for skill $currSkill")
                    positionHelper = getBreadth(currSkill)
                    screenHeight.value.maxHeight =
                        screenHeight.value.minHeight + (positionHelper.numNodes.max() * Y_DISTANCE)

                    // define parent node
                    val xPos = PARENT_X
                    val yPos = screenHeight.value.maxHeight / 2
                    val parentNode = mutableStateOf( SkillNode(
                        currSkill, positionHelper.breadth, xPos, yPos
                    )
                    )

                    val childrenNodes = instantiateChildrenNodes(
                        currSkill,
                        parentNode,
                        positionHelper,
                        screenHeight,
                        xPos
                    )

                    resultNodes.add(parentNode.value)
                    resultNodes.addAll(childrenNodes)

                    // update screen position
                    screenHeight.value.minHeight += Y_DISTANCE * (positionHelper.numNodes.max() + SPACE_BETWEEN_PARENTS)

                    Log.d("result nodes", resultNodes.toString())
                }
            }
            state.value.skills = resultNodes
        }
    }

    private fun instantiateChildrenNodes(
        parentSkill: Skill,
        parentNode: MutableState<SkillNode>,
        positionHelper: PositionHelper,
        screenHeight: MutableState<ScreenHeight>,
        currentX : Int
    ) : ArrayList<SkillNode> {
        val result = ArrayList<SkillNode>()
        val childrenNodes = ArrayList<SkillNode>()


        if (parentSkill.children != null) {
            var size = parentSkill.children!!.size
            for ((childCount, child) in parentSkill.children!!.withIndex()) {
                val xPos = currentX + X_DISTANCE
                val yPos = screenHeight.value.minHeight + (Y_DISTANCE * childCount)

                val childNode = mutableStateOf(
                    SkillNode(
                    child,
                    positionHelper.breadth--,
                    xPos,
                    yPos,
                    parent = parentNode.value
                )
                )

                if(child.children != null) {
                    val children = instantiateChildrenNodes(
                        child,
                        childNode,
                        PositionHelper(
                            positionHelper.numNodes,
                            positionHelper.breadth--
                        ),
                        screenHeight,
                        xPos
                    )

                    childNode.value.children = children
                    result.addAll(children)
                }
                childrenNodes.add(childNode.value)
            }
            parentNode.value.children = childrenNodes
        }

        result.addAll(childrenNodes)

        return result
    }

    private fun getBreadth(skill: Skill): PositionHelper {
        val positionHelper = PositionHelper(
            arrayListOf(1),
            0
        )
        val queue = LinkedList<Skill>()
        var currParent : Skill? = null
        var nodeCount = 0
        queue.add(skill)
        while(queue.isNotEmpty()) {
            val curr = queue.poll()
            nodeCount++
            if (curr != null) {
                if(currParent != curr.parent) {
                    positionHelper.breadth++
                    currParent = curr.parent
                    positionHelper.numNodes.add(nodeCount)
                    nodeCount = 0
                }
                if(curr.children != null)
                    for (child in curr.children!!) {
                        queue.add(child)
                    }
            }
        }
        return positionHelper
    }

    // update size if user pinches
    override fun setSize(size: Int) {
        TODO("Not yet implemented")
    }

    override fun setMaxX(max: Int) {
        TODO("Not yet implemented")
    }

    override fun setMaxY(max: Int) {
        TODO("Not yet implemented")
    }

}

// We add a state here, mainly only for adding functionality to update the max size of the LazyLayout when a new skill is added
data class State(
    var size: Int = 1_000,
    var maxX: Int = 10_000,
    var maxY: Int = 10_000,
    var skills: List<SkillNode> = emptyList()
)

// I sorta want to add a pinch to zoom thing, that will happen thru setSize. The setMaxX and setMaxY will happen when stuff changes so we don't need to worry
// too much about it
@Stable
interface Actions {
    fun setSize(size: Int)
    fun setMaxX(max: Int)
    fun setMaxY(max: Int)
}