package com.mobdeve.s12.mp.gamification.model

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mobdeve.s12.mp.gamification.R
import com.mobdeve.s12.mp.gamification.model.cosmetics.FeetCosmetic
import com.mobdeve.s12.mp.gamification.model.cosmetics.HeadCosmetic
import com.mobdeve.s12.mp.gamification.model.cosmetics.LegsCosmetic
import com.mobdeve.s12.mp.gamification.model.cosmetics.TorsoCosmetic

var default_head = HeadCosmetic(
    id = 0,
    name = "Default Head",
    cost = 0,
    image = R.drawable.cosmetic_default_head,
    description = "The default head"
)

var default_torso = TorsoCosmetic(
    id = 0,
    name = "Default Torso",
    cost = 0,
    image = R.drawable.cosmetic_default_torso,
    description = "The default torso"
)

var default_legs = LegsCosmetic(
    id = 0,
    name = "Default Legs",
    cost = 0,
    image = R.drawable.cosmetic_default_legs,
    description = "The default legs"
)

var default_feet = FeetCosmetic(
    id = 0,
    name = "Default Feet",
    cost = 0,
    image = R.drawable.cosmetic_default_feet,
    description = "The default feet"
)

class Avatar(
    var Head : HeadCosmetic = default_head,
    var Torso : TorsoCosmetic = default_torso,
    var Legs : LegsCosmetic = default_legs,
    var Feet : FeetCosmetic = default_feet) {

    @Composable
    fun ConstructAvatar(
        modifier : Modifier = Modifier
    ) {
        Box {
            Head.ViewCosmetic()
            Torso.ViewCosmetic()
            Legs.ViewCosmetic()
            Feet.ViewCosmetic()
        }
    }



}

@Composable
@Preview
private fun PreviewAvatar() {
    var Avatar : Avatar = Avatar()
    Avatar.ConstructAvatar()
}