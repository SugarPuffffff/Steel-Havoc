package com.sadi.steelhavoc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

data class BotCarState(
    val id: Int,
    val x: Float,
    val y: Float,
    val rotation: Float,
    val health: Int = 100
)

@Composable
fun EnemyCar(
    bot: BotCarState,
    cameraX: Float,
    cameraY: Float
) {

    val carImage = when (bot.id) {
        1 -> R.drawable.enemy_car_red
        2 -> R.drawable.enemy_car_blue
        3 -> R.drawable.enemy_car_green
        else -> R.drawable.enemy_car_red
    }

    Box(
        modifier = Modifier
            .offset(
                x = (bot.x - cameraX).dp,
                y = (bot.y - cameraY).dp
            )
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ENEMY HEALTH BAR
            EnemyHealthBar(
                health = bot.health
            )

            // Small gap
            Box(
                modifier = Modifier.size(5.dp)
            )

            // ENEMY CAR
            Image(
                painter = painterResource(
                    id = carImage
                ),

                contentDescription = "Enemy Car ${bot.id}",

                modifier = Modifier
                    .size(
                        width = 90.dp,
                        height = 140.dp
                    )
                    .graphicsLayer {
                        rotationZ = bot.rotation
                    },

                contentScale = ContentScale.Fit
            )
        }
    }
}