package com.mobdeve.s12.mp.gamification.model


class HeadCosmetic(
    id: Int,
    name: String,
    cost: Int,
    image: Int,
    description: String) :
    Cosmetic(id, name, cost, image, description) {
    override fun toString(): String {
        return super.toString()
    }
}

class TorsoCosmetic(
    id: Int,
    name: String,
    cost: Int,
    image: Int,
    description: String) :
    Cosmetic(id, name, cost, image, description) {
    override fun toString(): String {
        return super.toString()
    }
}

class LegsCosmetic(
    id: Int,
    name: String,
    cost: Int,
    image: Int,
    description: String) :
    Cosmetic(id, name, cost, image, description) {
    override fun toString(): String {
        return super.toString()
    }
}

class FeetCosmetic(
    id: Int,
    name: String,
    cost: Int,
    image: Int,
    description: String) :
    Cosmetic(id, name, cost, image, description) {
    override fun toString(): String {
        return super.toString()
    }
}