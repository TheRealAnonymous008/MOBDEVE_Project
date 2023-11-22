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
            cost = 10,
            image = context.resources.getResourceName(R.drawable.cosmetic_stark_head),
            description = "Stark from Frieren's short red hair"
        )
    )
    cosmeticList.add(
        TorsoCosmetic(
            id = 0,
            name = "Stark's Coat",
            cost = 10,
            image = context.resources.getResourceName(R.drawable.cosmetic_stark_torso),
            description = "Stark from Frieren's coat"
        )
    )
    cosmeticList.add(
        LegsCosmetic(
            id = 0,
            name = "Stark's Pants",
            cost = 10,
            image = context.resources.getResourceName(R.drawable.cosmetic_stark_legs),
            description = "Stark from Frieren's pants"
        )
    )
    cosmeticList.add(
        FeetCosmetic(
            id = 0,
            name = "Stark's Shoes",
            cost = 10,
            image = context.resources.getResourceName(R.drawable.cosmetic_stark_feet),
            description = "Stark from Frieren's shoes"
        )
    )
    cosmeticList.add(
        HeadCosmetic(
            id = 0,
            name = "Lena's Hair",
            cost = 10,
            image = context.resources.getResourceName(R.drawable.cosmetic_lena_head),
            description = "Lena from 86's head"
        )
    )
    cosmeticList.add(
        TorsoCosmetic(
            id = 0,
            name = "Lena's Coat",
            cost = 10,
            image = context.resources.getResourceName(R.drawable.cosmetic_lena_torso),
            description = "Lena from 86's torso"
        )
    )
    cosmeticList.add(
        LegsCosmetic(
            id = 0,
            name = "Lena's Pants",
            cost = 10,
            image = context.resources.getResourceName(R.drawable.cosmetic_lena_legs),
            description = "Lena from 86's pants"
        )
    )
    cosmeticList.add(
        FeetCosmetic(
            id = 0,
            name = "Lena's Shoes",
            cost = 10,
            image = context.resources.getResourceName(R.drawable.cosmetic_lena_feet),
            description = "Lena from 86's shoes"
        )
    )
    cosmeticList.add(
        HeadCosmetic(
            id = 0,
            name = "Black Hair",
            cost = 5,
            image = context.resources.getResourceName(R.drawable.cosmetic_darkhair_head),
            description = "Sleek black hair"
        )
    )
    cosmeticList.add(
        TorsoCosmetic(
            id = 0,
            name = "Green Shirt",
            cost = 5,
            image = context.resources.getResourceName(R.drawable.cosmetic_greenshirt_torso),
            description = "Nice shirt bro"
        )
    )
    cosmeticList.add(
        LegsCosmetic(
            id = 0,
            name = "Brown Pants",
            cost = 5,
            image = context.resources.getResourceName(R.drawable.cosmetic_brownpants_legs),
            description = "They're brown"
        )
    )
    cosmeticList.add(
        FeetCosmetic(
            id = 0,
            name = "White Shoes",
            cost = 5,
            image = context.resources.getResourceName(R.drawable.cosmetic_whiteshoes_feet),
            description = "Lena from 86's shoes"
        )
    )
    cosmeticList.add(
        HeadCosmetic(
            id = 0,
            name = "Black Long Hair",
            cost = 5,
            image = context.resources.getResourceName(R.drawable.cosmetic_longblackhair_head),
            description = "Black sleek hair but longer"
        )
    )
    cosmeticList.add(
        TorsoCosmetic(
            id = 0,
            name = "Plaid Torso",
            cost = 10,
            image = context.resources.getResourceName(R.drawable.cosmetic_plaid_torso),
            description = "Memories"
        )
    )
    cosmeticList.add(
        LegsCosmetic(
            id = 0,
            name = "Black Pants",
            cost = 10,
            image = context.resources.getResourceName(R.drawable.cosmetic_black_legs),
            description = "Cozy"
        )
    )
    cosmeticList.add(
        FeetCosmetic(
            id = 0,
            name = "Blue Sandals",
            cost = 10,
            image = context.resources.getResourceName(R.drawable.cosmetic_bluesandals_feet),
            description = "If you like sandals"
        )
    )
    

    return cosmeticList
}