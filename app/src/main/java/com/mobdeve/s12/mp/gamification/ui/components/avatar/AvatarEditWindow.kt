package com.mobdeve.s12.mp.gamification.ui.components.avatar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobdeve.s12.mp.gamification.model.Avatar
import com.mobdeve.s12.mp.gamification.model.Cosmetic
import com.mobdeve.s12.mp.gamification.model.createDefaultCosmeticList

const val AVATAR_SIZE = 120

@Composable
fun AvatarEditWindow(ownedCosmetics : ArrayList<Cosmetic>, avatar : Avatar, navController: NavController?) {
    Box {
        Row (
            modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
        ) {
            Column (
            ){
                var mod = Modifier.size((AVATAR_SIZE / 4).dp)
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
            )
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