package com.mobdeve.s12.mp.gamification.model


class HeadCosmetic(
    id: Long,
    name: String,
    cost: Int,
    image: String,
    description: String,
    owned : Boolean = false) :
    Cosmetic(id, name, cost, image, description, CosmeticTypes.HEAD, owned) {
}

class TorsoCosmetic(
    id: Long,
    name: String,
    cost: Int,
    image: String,
    description: String,
    owned : Boolean = false) :
    Cosmetic(id, name, cost, image, description, CosmeticTypes.TORSO, owned) {
}

class LegsCosmetic(
    id: Long,
    name: String,
    cost: Int,
    image: String,
    description: String,
    owned : Boolean = false) :
    Cosmetic(id, name, cost, image, description, CosmeticTypes.LEGS, owned) {
}

class FeetCosmetic(
    id: Long,
    name: String,
    cost: Int,
    image: String,
    description: String,
    owned : Boolean = false) :
    Cosmetic(id, name, cost, image, description, CosmeticTypes.FEET, owned) {
}