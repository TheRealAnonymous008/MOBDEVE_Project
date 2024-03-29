package com.mobdeve.s12.mp.gamification.model


import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.mobdeve.s12.mp.gamification.BuildConfig

enum class CosmeticTypes {
    HEAD, TORSO, LEGS, FEET
}

abstract class Cosmetic(
    val id: Long,
    val name: String,
    val cost: Int,
    val image: String,
    val description: String,
    val cosmeticTypes: CosmeticTypes,
    var owned : Boolean) {

    @Composable
    fun ViewCosmetic(
        modifier: Modifier = Modifier
    ) {
        Image(
            painter = painterResource(id = LocalContext.current.resources.getIdentifier(image, "drawable", BuildConfig.APPLICATION_ID)),
            contentDescription = "cosmetic_object",
            modifier = modifier)
    }

    fun setOwned() {
        this.owned = true;
    }

    override fun toString(): String {
        return this.name
    }

}

class CosmeticHolder {
    var cosmetics : ArrayList<Cosmetic> = ArrayList()

    fun get(str : String) : Cosmetic?{
        for (c in cosmetics){
            if(c.name == str){
                return c
            }
        }
        return null
    }

    fun getCosmetics() : List<Cosmetic>{
        return cosmetics.toList()
    }

    fun add(t: Cosmetic){
        cosmetics.add(t)
    }

    fun remove(t : Cosmetic){
        cosmetics.remove(t)
    }

    fun update(pos : Int, payload : Cosmetic){
        cosmetics[pos] = payload
    }

    fun clear() {
        cosmetics.clear()
    }

    fun getSorted() : List<Cosmetic>{
        return cosmetics.sortedBy { it.name }
    }
}