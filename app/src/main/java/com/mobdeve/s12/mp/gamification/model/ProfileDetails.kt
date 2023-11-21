package com.mobdeve.s12.mp.gamification.model

import java.io.Serializable

data class ProfileDetails(
    var name : String,
    var description : String,
    var avatar : Avatar,
    var currency : Int
) : Serializable {

    fun addCurrency(amount : Int) {
        currency += amount
    }

    fun removeCurrency(amount : Int) {
        currency -= amount
    }
}

