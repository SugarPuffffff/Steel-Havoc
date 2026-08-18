package com.sadi.steelhavoc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BulletView(
    bullet: BulletState,
    cameraX: Float,
    cameraY: Float
) {

    if (!bullet.active) {
        return
    }

    Box(
        modifier = Modifier
            .offset(
                x = (bullet.x - cameraX).dp,
                y = (bullet.y - cameraY).dp
            )
            .size(
                width = 10.dp,
                height = 18.dp
            )
            .background(
                Color(0xFFFFC107)
            )
    )
}