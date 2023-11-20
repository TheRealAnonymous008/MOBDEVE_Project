package com.mobdeve.s12.mp.gamification.model


class HeadCosmetic(
    id: Long,
    name: String,
    cost: Int,
    image: String,
    description: String) :
    Cosmetic(id, name, cost, image, description, CosmeticTypes.HEAD) {
    override fun toString(): String {
        return super.toString()
    }
}

class TorsoCosmetic(
    id: Long,
    name: String,
    cost: Int,
    image: String,
    description: String) :
    Cosmetic(id, name, cost, image, description, CosmeticTypes.TORSO) {
    override fun toString(): String {
        return super.toString()
    }
}

class LegsCosmetic(
    id: Long,
    name: String,
    cost: Int,
    image: String,
    description: String) :
    Cosmetic(id, name, cost, image, description, CosmeticTypes.LEGS) {
    override fun toString(): String {
        return super.toString()
    }
}

class FeetCosmetic(
    id: Long,
    name: String,
    cost: Int,
    image: String,
    description: String) :
    Cosmetic(id, name, cost, image, description, CosmeticTypes.FEET) {
    override fun toString(): String {
        return super.toString()
    }
}