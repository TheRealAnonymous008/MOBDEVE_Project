package com.mobdeve.s12.mp.gamification.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.gson.Gson
import com.mobdeve.s12.mp.gamification.R

var default_head = HeadCosmetic(
    id = 0,
    name = "Default Head",
    cost = 0,
    image = "cosmetic_default_head",
    description = "The default head"
)

var default_torso = TorsoCosmetic(
    id = 0,
    name = "Default Torso",
    cost = 0,
    image = "cosmetic_default_torso",
    description = "The default torso"
)

var default_legs = LegsCosmetic(
    id = 0,
    name = "Default Legs",
    cost = 0,
    image = "cosmetic_default_legs",
    description = "The default legs"
)

var default_feet = FeetCosmetic(
    id = 0,
    name = "Default Feet",
    cost = 0,
    image = "cosmetic_default_feet",
    description = "The default feet"
)

class Avatar(
    var head : HeadCosmetic = default_head,
    var torso : TorsoCosmetic = default_torso,
    var legs : LegsCosmetic = default_legs,
    var feet : FeetCosmetic = default_feet) {

    // Convert Avatar to JSON string
    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        // Create Avatar from JSON string
        fun fromJson(json: String): Avatar {
            return Gson().fromJson(json, Avatar::class.java)
        }
    }

    @Composable
    fun ConstructAvatar(
        modifier : Modifier = Modifier,
        background : Color = Color.Transparent
    ) {
        Box(
            Modifier.background(background)
        ) {
            head.ViewCosmetic(
                modifier = modifier
            )
            torso.ViewCosmetic(
                modifier = modifier
            )
            legs.ViewCosmetic(
                modifier = modifier
            )
            feet.ViewCosmetic(
                modifier = modifier
            )
        }
    }



}

@Composable
@Preview
private fun PreviewAvatar() {
    var Avatar : Avatar = Avatar()
    Avatar.ConstructAvatar()
}
