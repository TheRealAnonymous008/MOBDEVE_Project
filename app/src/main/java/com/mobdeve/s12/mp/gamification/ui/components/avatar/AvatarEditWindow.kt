package com.mobdeve.s12.mp.gamification.ui.components.avatar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobdeve.s12.mp.gamification.model.Avatar
import com.mobdeve.s12.mp.gamification.model.Cosmetic
import com.mobdeve.s12.mp.gamification.model.FeetCosmetic
import com.mobdeve.s12.mp.gamification.model.HeadCosmetic
import com.mobdeve.s12.mp.gamification.model.LegsCosmetic
import com.mobdeve.s12.mp.gamification.model.TorsoCosmetic
import com.mobdeve.s12.mp.gamification.model.createDefaultCosmeticList
import com.mobdeve.s12.mp.gamification.ui.components.cosmetics.CosmeticContainer
import com.mobdeve.s12.mp.gamification.ui.components.cosmetics.CosmeticRow
import com.mobdeve.s12.mp.gamification.ui.theme.SecondaryColor


const val AVATAR_DISPLAY_SCALER = 3
@Composable
fun AvatarEditWindow(
    ownedCosmetics: ArrayList<Cosmetic>,
    avatar: Avatar,
    navController: NavController?
) {
    var AVATAR_SIZE = LocalConfiguration.current.screenHeightDp / AVATAR_DISPLAY_SCALER
    val headList: ArrayList<HeadCosmetic> = ArrayList()
    val torsoList: ArrayList<TorsoCosmetic> = ArrayList()
    val legsList: ArrayList<LegsCosmetic> = ArrayList()
    val feetList: ArrayList<FeetCosmetic> = ArrayList()

    var avatar_state = remember { mutableStateOf(avatar) }

    for (i in ownedCosmetics) {
        if (i is HeadCosmetic)
            headList.add(i)
        if (i is TorsoCosmetic)
            torsoList.add(i)
        if (i is LegsCosmetic)
            legsList.add(i)
        if (i is FeetCosmetic)
            feetList.add(i)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryColor),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Max)
                    .padding(5.dp, 5.dp)
            ) {
                Column {
                    var mod = Modifier
                        .size((AVATAR_SIZE / 4).dp)
                        .border(1.dp, Color.Black)

                    Row {
                        avatar.head.ViewCosmetic(mod)
                    }
                    Row {
                        avatar.torso.ViewCosmetic(mod)
                    }
                    Row {
                        avatar.legs.ViewCosmetic(mod)
                    }
                    Row {
                        avatar.feet.ViewCosmetic(mod)
                    }
                }
                avatar.ConstructAvatar(
                    modifier = Modifier
                        .size(AVATAR_SIZE.dp)
                        .border(BorderStroke(1.dp, Color.Black))
                        .offset(x = -1.dp) // hide left side of border
                )
            }
            
            CosmeticRow(headList) { cosmetic: Cosmetic ->
                CosmeticContainer(cosmetic)
                {
                    equipCosmetic(cosmetic, avatar_state.value)
                }
            }
            CosmeticRow(torsoList) { cosmetic: Cosmetic ->
                CosmeticContainer(cosmetic)
                {
                    equipCosmetic(cosmetic, avatar_state.value)
                }
            }
            CosmeticRow(legsList) { cosmetic: Cosmetic ->
                CosmeticContainer(cosmetic)
                {
                    equipCosmetic(cosmetic, avatar_state.value)
                }
            }
            CosmeticRow(feetList) { cosmetic: Cosmetic ->
                CosmeticContainer(cosmetic)
                {
                    equipCosmetic(cosmetic, avatar_state.value)
                }
            }
        }

    }
}


@Composable
@Preview
fun PreviewAvatarSelection() {
    val ownedCosmetics = createDefaultCosmeticList()
    val avatar = Avatar()
    AvatarEditWindow(ownedCosmetics = ownedCosmetics, avatar = avatar, null)
}

fun equipCosmetic(cosmetic : Cosmetic, avatar : Avatar) {
    if(cosmetic is HeadCosmetic)
        avatar.head = cosmetic
    if(cosmetic is TorsoCosmetic)
        avatar.torso = cosmetic
    if(cosmetic is LegsCosmetic)
        avatar.legs = cosmetic
    if(cosmetic is FeetCosmetic)
        avatar.feet = cosmetic
}