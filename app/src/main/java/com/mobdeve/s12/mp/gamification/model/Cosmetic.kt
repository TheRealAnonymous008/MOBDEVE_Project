package com.mobdeve.s12.mp.gamification.model

import android.media.Image
import com.mobdeve.s12.mp.gamification.R
import java.io.Serializable

enum class CosmeticType {
    HAT, TOP, BOTTOM, FOOTWEAR
}

data class Cosmetic (
    var name : String,
    var cost : Int,
    var image : Int,
    var description : String,
    var cosmeticType : Enum<CosmeticType>
) : Serializable

fun createDefaultCosmeticList() : ArrayList<Cosmetic> {
    val cosmeticList : ArrayList<Cosmetic> = ArrayList<Cosmetic>()

    cosmeticList.add(Cosmetic("Fedora", 10, R.drawable.potato, "This is a potato",CosmeticType.HAT))
    cosmeticList.add(Cosmetic("Hutao Hat", 100000, R.drawable.hutaohat,"This is Hutao's hat from hit game Genshin Impact", CosmeticType.HAT))
    cosmeticList.add(Cosmetic("Joaquin Face Hat", 51, R.drawable.potato,"This is a potato", CosmeticType.HAT))
    cosmeticList.add(Cosmetic("Jared Face Hat", 51, R.drawable.potato,"This is a potato", CosmeticType.HAT))

    cosmeticList.add(Cosmetic("Gray Polo Shirt", 10, R.drawable.potato,"This is a potato", CosmeticType.TOP))
    cosmeticList.add(Cosmetic("Red Polo Shirt", 10, R.drawable.potato,"This is a potato", CosmeticType.TOP))
    cosmeticList.add(Cosmetic("Blue Polo Shirt", 10, R.drawable.potato,"This is a potato", CosmeticType.TOP))

    cosmeticList.add(Cosmetic("Red Shorts", 10, R.drawable.potato,"This is a potato", CosmeticType.BOTTOM))
    cosmeticList.add(Cosmetic("Blue Shorts", 10, R.drawable.potato,"This is a potato", CosmeticType.BOTTOM))
    cosmeticList.add(Cosmetic("Green Shorts", 10, R.drawable.potato,"This is a potato", CosmeticType.BOTTOM))

    cosmeticList.add(Cosmetic("Red Slippers", 10, R.drawable.potato,"This is a potato", CosmeticType.FOOTWEAR))
    cosmeticList.add(Cosmetic("Blue Slippers", 10, R.drawable.potato,"This is a potato", CosmeticType.FOOTWEAR))
    cosmeticList.add(Cosmetic("Green Slippers", 10, R.drawable.potato,"This is a potato", CosmeticType.FOOTWEAR))

    return cosmeticList
}