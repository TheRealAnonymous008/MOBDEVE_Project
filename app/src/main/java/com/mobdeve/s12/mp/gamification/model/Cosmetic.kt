package com.mobdeve.s12.mp.gamification.model


import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.mobdeve.s12.mp.gamification.BuildConfig
import com.mobdeve.s12.mp.gamification.R

enum class CosmeticTypes {
    HEAD, TORSO, LEGS, FEET
}

abstract class Cosmetic(
    val id: Long,
    val name: String,
    val cost: Int,
    val image: String,
    val description: String,
    val cosmeticTypes: CosmeticTypes) {

    @Composable
    fun ViewCosmetic(
        modifier: Modifier = Modifier
    ) {
        Image(
            painter = painterResource(id = Resources.getSystem().getIdentifier(image, "drawable", BuildConfig.APPLICATION_ID)),
            contentDescription = "cosmetic_object",
            modifier = modifier)
    }

    override fun toString(): String {
        return "${this.name}"
    }

}
fun createDefaultCosmeticList() : ArrayList<Cosmetic> {
    val cosmeticList : ArrayList<Cosmetic> = ArrayList<Cosmetic>()

    cosmeticList.add(
        HeadCosmetic(
        id = 0,
        name = "Default Head",
        cost = 0,
        image = Resources.getSystem().getResourceName(R.drawable.cosmetic_default_head),
        description = "The default head"
    )
    )
    cosmeticList.add(
        TorsoCosmetic(
        id = 0,
        name = "Default Torso",
        cost = 0,
        image = Resources.getSystem().getResourceName(R.drawable.cosmetic_default_torso),
        description = "The default torso"
    )
    )
    cosmeticList.add(
        LegsCosmetic(
        id = 0,
        name = "Default Legs",
        cost = 0,
        image = Resources.getSystem().getResourceName(R.drawable.cosmetic_default_legs),
        description = "The default legs"
    )
    )
    cosmeticList.add(
        FeetCosmetic(
        id = 0,
        name = "Default Feet",
        cost = 0,
        image = Resources.getSystem().getResourceName(R.drawable.cosmetic_default_feet),
        description = "The default feet"
    )
    )
    cosmeticList.add(
        HeadCosmetic(
        id = 0,
        name = "Stark's Hair",
        cost = 0,
        image = Resources.getSystem().getResourceName(R.drawable.cosmetic_stark_head),
        description = "Short red hair with a dark contrasting black in the center."
    )
    )
    cosmeticList.add(
        TorsoCosmetic(
        id = 0,
        name = "Stark's Coat",
        cost = 0,
        image = Resources.getSystem().getResourceName(R.drawable.cosmetic_stark_torso),
        description = "Stark's coat"
    )
    )
    cosmeticList.add(
        LegsCosmetic(
        id = 0,
        name = "Stark's Pants",
        cost = 0,
        image = Resources.getSystem().getResourceName(R.drawable.cosmetic_stark_legs),
        description = "Stark's pants"
    )
    )
    cosmeticList.add(
        FeetCosmetic(
        id = 0,
        name = "Stark's Shoes",
        cost = 0,
        image = Resources.getSystem().getResourceName(R.drawable.cosmetic_stark_feet),
        description = "Stark's shoes"
    )
    )
    return cosmeticList
}