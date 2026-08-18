package com.sadi.steelhavoc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EnemyHealthBar(
    health: Int
) {

    val safeHealth =
        health.coerceIn(
            0,
            100
        )

    Box(
        modifier = Modifier
            .width(80.dp)
            .height(8.dp)
            .background(
                Color(0xFF2B2B2B)
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(
                    fraction =
                        safeHealth / 100f
                )
                .height(8.dp)
                .background(
                    Color.Red
                )
        )
    }
}