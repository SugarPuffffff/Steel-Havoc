package com.sadi.steelhavoc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CombatMap(
    cameraX: Float,
    cameraY: Float
) {

    Box(
        modifier = Modifier
            .requiredSize(
                width = 1600.dp,
                height = 2200.dp
            )
            .offset(
                x = (-cameraX).dp,
                y = (-cameraY).dp
            )
            .background(Color(0xFF505050))
    ) {

        // =========================
        // MAIN VERTICAL ROAD
        // =========================
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(
                    width = 300.dp,
                    height = 2100.dp
                )
                .background(Color(0xFF303030))
        )

        // =========================
        // MAIN HORIZONTAL ROAD
        // =========================
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(
                    width = 1500.dp,
                    height = 280.dp
                )
                .background(Color(0xFF303030))
        )

        // =========================
        // SECOND ROAD - TOP
        // =========================
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-650).dp)
                .size(
                    width = 1200.dp,
                    height = 200.dp
                )
                .background(Color(0xFF363636))
        )

        // =========================
        // SECOND ROAD - BOTTOM
        // =========================
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 650.dp)
                .size(
                    width = 1200.dp,
                    height = 200.dp
                )
                .background(Color(0xFF363636))
        )

        // =========================
        // VERTICAL ROAD LINES
        // =========================
        for (i in -9..9) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (i * 105).dp)
                    .size(
                        width = 10.dp,
                        height = 55.dp
                    )
                    .background(Color(0xFFD6D6D6))
            )
        }

        // =========================
        // HORIZONTAL ROAD LINES
        // =========================
        for (i in -6..6) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = (i * 110).dp)
                    .size(
                        width = 60.dp,
                        height = 10.dp
                    )
                    .background(Color(0xFFD6D6D6))
            )
        }

        // =========================
        // TOP ROAD LINES
        // =========================
        for (i in -4..4) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(
                        x = (i * 120).dp,
                        y = (-650).dp
                    )
                    .size(
                        width = 65.dp,
                        height = 8.dp
                    )
                    .background(Color(0xFFBDBDBD))
            )
        }

        // =========================
        // BOTTOM ROAD LINES
        // =========================
        for (i in -4..4) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(
                        x = (i * 120).dp,
                        y = 650.dp
                    )
                    .size(
                        width = 65.dp,
                        height = 8.dp
                    )
                    .background(Color(0xFFBDBDBD))
            )
        }

        // =========================
        // BUILDING / WALL BLOCKS
        // =========================

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(
                    x = (-500).dp,
                    y = (-400).dp
                )
                .size(
                    width = 300.dp,
                    height = 350.dp
                )
                .background(Color(0xFF5D4037))
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(
                    x = 500.dp,
                    y = (-400).dp
                )
                .size(
                    width = 320.dp,
                    height = 380.dp
                )
                .background(Color(0xFF4E342E))
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(
                    x = (-500).dp,
                    y = 400.dp
                )
                .size(
                    width = 340.dp,
                    height = 380.dp
                )
                .background(Color(0xFF3E2723))
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(
                    x = 500.dp,
                    y = 420.dp
                )
                .size(
                    width = 320.dp,
                    height = 350.dp
                )
                .background(Color(0xFF5D4037))
        )

        // =========================
        // CRATES / OBSTACLES
        // =========================

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(
                    x = (-360).dp,
                    y = (-200).dp
                )
                .size(80.dp)
                .background(Color(0xFF8D6E63))
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(
                    x = 360.dp,
                    y = (-220).dp
                )
                .size(90.dp)
                .background(Color(0xFF795548))
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(
                    x = (-380).dp,
                    y = 220.dp
                )
                .size(85.dp)
                .background(Color(0xFF795548))
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(
                    x = 380.dp,
                    y = 250.dp
                )
                .size(90.dp)
                .background(Color(0xFF8D6E63))
        )

        // =========================
        // OUTER WORLD BOUNDARY
        // =========================

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(
                    width = 1600.dp,
                    height = 35.dp
                )
                .background(Color.Black)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(
                    width = 1600.dp,
                    height = 35.dp
                )
                .background(Color.Black)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(
                    width = 35.dp,
                    height = 2200.dp
                )
                .background(Color.Black)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(
                    width = 35.dp,
                    height = 2200.dp
                )
                .background(Color.Black)
        )
    }
}