package com.mobdeve.s12.mp.gamification.helpers

import android.content.Context
import com.mobdeve.s12.mp.gamification.R
import com.mobdeve.s12.mp.gamification.model.Cosmetic
import com.mobdeve.s12.mp.gamification.model.FeetCosmetic
import com.mobdeve.s12.mp.gamification.model.HeadCosmetic
import com.mobdeve.s12.mp.gamification.model.LegsCosmetic
import com.mobdeve.s12.mp.gamification.model.TorsoCosmetic

fun initializeCosmetics(context: Context) : ArrayList<Cosmetic> {
    val cosmeticList : ArrayList<Cosmetic> = ArrayList<Cosmetic>()

    cosmeticList.add(
        HeadCosmetic(
            id = 0,
            name = "Default Head",
            cost = 0,
            image = context.resources.getResourceName(R.drawable.cosmetic_default_head),
            description = "The default head",
            true
        )
    )
    cosmeticList.add(
        TorsoCosmetic(
            id = 0,
            name = "Default Torso",
            cost = 0,
            image = context.resources.getResourceName(R.drawable.cosmetic_default_torso),
            description = "The default torso", true
        )
    )
    cosmeticList.add(
        LegsCosmetic(
            id = 0,
            name = "Default Legs",
            cost = 0,
            image = context.resources.getResourceName(R.drawable.cosmetic_default_legs),
            description = "The default legs", true
        )
    )
    cosmeticList.add(
        FeetCosmetic(
            id = 0,
            name = "Default Feet",
            cost = 0,
            image = context.resources.getResourceName(R.drawable.cosmetic_default_feet),
            description = "The default feet", true
        )
    )
    cosmeticList.add(
        HeadCosmetic(
            id = 0,
            name = "Stark's Hair",
            cost = 2,
            image = context.resources.getResourceName(R.drawable.cosmetic_stark_head),
            description = "Short red hair with a dark contrasting black in the center."
        )
    )
    cosmeticList.add(
        TorsoCosmetic(
            id = 0,
            name = "Stark's Coat",
            cost = 2,
            image = context.resources.getResourceName(R.drawable.cosmetic_stark_torso),
            description = "Stark's coat"
        )
    )
    cosmeticList.add(
        LegsCosmetic(
            id = 0,
            name = "Stark's Pants",
            cost = 2,
            image = context.resources.getResourceName(R.drawable.cosmetic_stark_legs),
            description = "Stark's pants"
        )
    )
    cosmeticList.add(
        FeetCosmetic(
            id = 0,
            name = "Stark's Shoes",
            cost = 2,
            image = context.resources.getResourceName(R.drawable.cosmetic_stark_feet),
            description = "Stark's shoes"
        )
    )

    return cosmeticList
}