package com.sadi.steelhavoc

data class RectObstacle(
    val centerX: Float,
    val centerY: Float,
    val width: Float,
    val height: Float
)

fun isPositionBlocked(
    x: Float,
    y: Float
): Boolean {

    // Player collision radius
    val playerRadius = 45f

    val obstacles = listOf(

        // BUILDING 1
        RectObstacle(
            centerX = -500f,
            centerY = -400f,
            width = 300f,
            height = 350f
        ),

        // BUILDING 2
        RectObstacle(
            centerX = 500f,
            centerY = -400f,
            width = 320f,
            height = 380f
        ),

        // BUILDING 3
        RectObstacle(
            centerX = -500f,
            centerY = 400f,
            width = 340f,
            height = 380f
        ),

        // BUILDING 4
        RectObstacle(
            centerX = 500f,
            centerY = 420f,
            width = 320f,
            height = 350f
        ),

        // CRATE 1
        RectObstacle(
            centerX = -360f,
            centerY = -200f,
            width = 80f,
            height = 80f
        ),

        // CRATE 2
        RectObstacle(
            centerX = 360f,
            centerY = -220f,
            width = 90f,
            height = 90f
        ),

        // CRATE 3
        RectObstacle(
            centerX = -380f,
            centerY = 220f,
            width = 85f,
            height = 85f
        ),

        // CRATE 4
        RectObstacle(
            centerX = 380f,
            centerY = 250f,
            width = 90f,
            height = 90f
        )
    )

    for (obstacle in obstacles) {

        val left =
            obstacle.centerX -
                    obstacle.width / 2 -
                    playerRadius

        val right =
            obstacle.centerX +
                    obstacle.width / 2 +
                    playerRadius

        val top =
            obstacle.centerY -
                    obstacle.height / 2 -
                    playerRadius

        val bottom =
            obstacle.centerY +
                    obstacle.height / 2 +
                    playerRadius

        if (
            x in left..right &&
            y in top..bottom
        ) {
            return true
        }
    }

    return false
}