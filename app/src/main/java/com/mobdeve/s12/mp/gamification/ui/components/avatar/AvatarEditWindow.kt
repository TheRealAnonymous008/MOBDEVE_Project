package com.mobdeve.s12.mp.gamification.ui.components.avatar

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobdeve.s12.mp.gamification.model.Avatar
import com.mobdeve.s12.mp.gamification.model.Cosmetic
import com.mobdeve.s12.mp.gamification.model.FeetCosmetic
import com.mobdeve.s12.mp.gamification.model.HeadCosmetic
import com.mobdeve.s12.mp.gamification.model.LegsCosmetic
import com.mobdeve.s12.mp.gamification.model.Profile
import com.mobdeve.s12.mp.gamification.model.ProfileViewModel
import com.mobdeve.s12.mp.gamification.model.TorsoCosmetic
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
    val AVATAR_SIZE = LocalConfiguration.current.screenHeightDp / AVATAR_DISPLAY_SCALER

    val avatarState = remember {mutableStateOf(avatar)}
    val profileViewModel: ProfileViewModel = ProfileViewModel(context = LocalContext.current)

    key(avatarState.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SecondaryColor),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .height(intrinsicSize = IntrinsicSize.Max)
                        .padding(5.dp, 5.dp)
                ) {
                    AvatarView(
                        AVATAR_SIZE,
                        avatarState = avatarState.value,
                        onAvatarStateChange = {
                            avatarState.value = it
                        })
                }
                AvatarEdit(
                    ownedCosmetics = ownedCosmetics,
                    avatar,
                    avatarState = avatarState.value,
                    onAvatarStateChange = {
                        avatarState.value = it
                        profileViewModel.updateAvatar(it)
                    })
            }
        }
    }
}

@Composable
fun AvatarView(avatar_size: Int, avatarState: Avatar, onAvatarStateChange: (Avatar) -> Unit) {
    Column {
        val mod = Modifier
            .size((avatar_size / 4).dp)
            .border(1.dp, Color.Black)

        Row {
            avatarState.head.ViewCosmetic(mod)
        }
        Row {
            avatarState.torso.ViewCosmetic(mod)
        }
        Row {
            avatarState.legs.ViewCosmetic(mod)
        }
        Row {
            avatarState.feet.ViewCosmetic(mod)
        }
    }
    avatarState.ConstructAvatar(
        modifier = Modifier
            .size(avatar_size.dp)
            .border(BorderStroke(1.dp, Color.Black))
            .offset(x = -1.dp) // hide left side of border
    )
}

@Composable
fun AvatarEdit(
    ownedCosmetics: ArrayList<Cosmetic>,
    avatar: Avatar,
    avatarState: Avatar,
    onAvatarStateChange: (Avatar) -> Unit
) {
    val headList: ArrayList<HeadCosmetic> = ArrayList()
    val torsoList: ArrayList<TorsoCosmetic> = ArrayList()
    val legsList: ArrayList<LegsCosmetic> = ArrayList()
    val feetList: ArrayList<FeetCosmetic> = ArrayList()

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

    val avatar_holder =
        Avatar(avatarState.head, avatarState.torso, avatarState.legs, avatarState.feet)

    CosmeticRow(headList) { cosmetic: Cosmetic ->
        CosmeticContainer(cosmetic)
        {
            if (cosmetic is HeadCosmetic) {
                avatar_holder.head = cosmetic
                avatar.head = cosmetic
            }

            onAvatarStateChange(avatar_holder)
        }
    }
    CosmeticRow(torsoList) { cosmetic: Cosmetic ->
        CosmeticContainer(cosmetic)
        {
            if (cosmetic is TorsoCosmetic) {
                avatar_holder.torso = cosmetic
                avatar.torso = cosmetic
            }
            onAvatarStateChange(avatar_holder)
        }
    }
    CosmeticRow(legsList) { cosmetic: Cosmetic ->
        CosmeticContainer(cosmetic)
        {
            if (cosmetic is LegsCosmetic) {
                avatar_holder.legs = cosmetic
                avatar.legs = cosmetic
            }
            onAvatarStateChange(avatar_holder)
        }
    }
    CosmeticRow(feetList) { cosmetic: Cosmetic ->
        CosmeticContainer(cosmetic)
        {
            if (cosmetic is FeetCosmetic) {
                avatar_holder.feet = cosmetic
                avatar.feet = cosmetic
            }

            onAvatarStateChange(avatar_holder)
        }
    }
}
