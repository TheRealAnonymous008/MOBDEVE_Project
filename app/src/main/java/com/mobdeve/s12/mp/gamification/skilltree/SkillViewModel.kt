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
import java.util.Stack


const val X_SCALE = 300
const val Y_SCALE = 300
const val Y_DISTANCE = 400
const val INIT_HEIGHT = 300

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
class SkillViewModel(val skills : ArrayList<Skill>): ViewModel(), Actions {
    val state = MutableStateFlow(State())
    // store latest job (idk why we do this) they're like async calls
    private var skillViewGenerationJob: Job? = null
        set(value) {
            field?.cancel()
            field = value
        }

    init {
        Log.e("A", skills.size.toString())
        generateNodes(skills)
    }

    private fun generateNodes(skills : List<Skill>) {
        skillViewGenerationJob = viewModelScope.launch (Dispatchers.Default) {
            var screenHeight = mutableStateOf( ScreenHeight(
                minHeight = INIT_HEIGHT,
                maxHeight = Y_DISTANCE
            )
            )

            // First pass. Convert all the skills to skill Nodes.
            var resultNodes = convertToSkillNodes()
            // Then add all children. At the same time, update the breadths and depths respectively
            resultNodes = processChildren(resultNodes)

            resultNodes.forEach {
                Log.d("Hello", "${it.xPos} ,${it.yPos} ${it.skill.name}")
            }

            state.value.skills = resultNodes
        }
    }

    private fun convertToSkillNodes() : ArrayList<SkillNode>{
        var skillNodes = ArrayList<SkillNode>()

        // First get all nodes
        skills.forEach { skill ->
            val skillNode = SkillNode(
                skill = skill,
                breadth = 0,
                yPos = 0,
                xPos = 0
            )
            skillNodes.add(skillNode)
        }
        return skillNodes
    }

    private fun findRoots() : ArrayList<Skill> {
        val roots = ArrayList<Skill>()

        // Find all the root nodes
        skills.forEach {
            if (it.parent == null) {
                roots.add(it)
            }
        }
        return roots
    }

    private fun processChildren(skillNodes : ArrayList<SkillNode>)  : ArrayList<SkillNode>{
        // The map makes it convenient to access the skills
        val skillNodesMap = skillNodes.associateBy { it.skill.id }
        val roots = findRoots()

        // Stack records skill and depth
        val skillStack = Stack<Pair<Skill, Int>>()
        var cumY = 0

        roots.forEach {root ->
            run {
                skillStack.push(Pair(root, 0))
                var currentDepth = 0

                while (!skillStack.empty()) {
                    // This indicates that we traversed up one depth level
                    val frame = skillStack.pop()
                    val current = frame.first
                    val depth  = frame.second
                    val currentSkillNode = skillNodes[current.id]

                    if (currentDepth >= depth) {
                        cumY ++
                    }

                    currentDepth = depth


                    // This handles positioning TODO: Might need to fix it.
                    currentSkillNode.xPos = (depth) * X_SCALE
                    currentSkillNode.yPos = (cumY - 1) * Y_SCALE

                    // Do two things. First, add to the skill stack and second, add children
                    current.children.forEach {child ->
                        skillNodesMap[current.id]!!.addChild(skillNodesMap[child.id]!!)
                        skillStack.push(Pair(child, depth + 1))
                    }
                }
            }
        }

        return skillNodes
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