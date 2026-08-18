package com.sadi.steelhavoc

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun ExplosionEffect(
    worldX: Float,
    worldY: Float,
    cameraX: Float,
    cameraY: Float,
    onFinished: () -> Unit
) {

    val scale = remember {
        Animatable(0.25f)
    }

    val alpha = remember {
        Animatable(1f)
    }

    LaunchedEffect(Unit) {

        // Explosion grow
        scale.animateTo(
            targetValue = 1.8f,
            animationSpec = tween(
                durationMillis = 350
            )
        )

        // Explosion fade out
        alpha.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 220
            )
        )

        onFinished()
    }

    // Full screen coordinate system
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        // OUTER ORANGE BLAST
        Box(
            modifier = Modifier
                .offset(
                    x = (worldX - cameraX).dp,
                    y = (worldY - cameraY).dp
                )
                .size(110.dp)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                    this.alpha = alpha.value
                }
                .background(
                    color = Color(0xFFFF6D00),
                    shape = CircleShape
                )
        )

        // INNER YELLOW FIRE
        Box(
            modifier = Modifier
                .offset(
                    x = (worldX - cameraX).dp,
                    y = (worldY - cameraY).dp
                )
                .size(65.dp)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                    this.alpha = alpha.value
                }
                .background(
                    color = Color(0xFFFFD600),
                    shape = CircleShape
                )
        )

        // HOT WHITE CENTER
        Box(
            modifier = Modifier
                .offset(
                    x = (worldX - cameraX).dp,
                    y = (worldY - cameraY).dp
                )
                .size(28.dp)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                    this.alpha = alpha.value
                }
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
        )
    }
}