package com.mobdeve.s12.mp.gamification.model


class HeadCosmetic(
    id: Long,
    name: String,
    cost: Int,
    image: String,
    description: String) :
    Cosmetic(id, name, cost, image, description, CosmeticTypes.HEAD) {
}

class TorsoCosmetic(
    id: Long,
    name: String,
    cost: Int,
    image: String,
    description: String) :
    Cosmetic(id, name, cost, image, description, CosmeticTypes.TORSO) {
}

class LegsCosmetic(
    id: Long,
    name: String,
    cost: Int,
    image: String,
    description: String) :
    Cosmetic(id, name, cost, image, description, CosmeticTypes.LEGS) {
}

class FeetCosmetic(
    id: Long,
    name: String,
    cost: Int,
    image: String,
    description: String) :
    Cosmetic(id, name, cost, image, description, CosmeticTypes.FEET) {
}