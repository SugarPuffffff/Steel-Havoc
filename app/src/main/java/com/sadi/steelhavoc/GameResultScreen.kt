package com.sadi.steelhavoc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameResultScreen(
    playerWon: Boolean,
    onRestart: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xDD000000)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {

            Text(
                text = if (playerWon) {
                    "YOU WIN"
                } else {
                    "GAME OVER"
                },
                color = if (playerWon) {
                    Color.Green
                } else {
                    Color.Red
                },
                fontSize = 42.sp
            )

            Text(
                text = if (playerWon) {
                    "All enemies destroyed!"
                } else {
                    "Your vehicle was destroyed!"
                },
                color = Color.White,
                fontSize = 18.sp
            )

            Button(
                onClick = onRestart,
                modifier = Modifier
                    .width(180.dp)
                    .height(60.dp)
            ) {

                Text(
                    text = "RESTART",
                    fontSize = 18.sp
                )
            }
        }
    }
}