package com.sadi.steelhavoc

import kotlin.math.sqrt

fun isCarCollision(
    x1: Float,
    y1: Float,
    x2: Float,
    y2: Float,
    collisionDistance: Float = 95f
): Boolean {

    val dx = x1 - x2
    val dy = y1 - y2

    val distance = sqrt(
        dx * dx + dy * dy
    )

    return distance < collisionDistance
}

fun isPlayerBlockedByBots(
    playerX: Float,
    playerY: Float,
    bot1: BotCarState,
    bot2: BotCarState,
    bot3: BotCarState
): Boolean {

    if (
        bot1.health > 0 &&
        isCarCollision(
            playerX,
            playerY,
            bot1.x,
            bot1.y
        )
    ) {
        return true
    }

    if (
        bot2.health > 0 &&
        isCarCollision(
            playerX,
            playerY,
            bot2.x,
            bot2.y
        )
    ) {
        return true
    }

    if (
        bot3.health > 0 &&
        isCarCollision(
            playerX,
            playerY,
            bot3.x,
            bot3.y
        )
    ) {
        return true
    }

    return false
}

fun isBotBlockedByCars(
    botId: Int,
    nextX: Float,
    nextY: Float,

    playerX: Float,
    playerY: Float,
    playerHealth: Int,

    bot1: BotCarState,
    bot2: BotCarState,
    bot3: BotCarState
): Boolean {

    // =========================
    // PLAYER COLLISION
    // =========================

    if (
        playerHealth > 0 &&
        isCarCollision(
            nextX,
            nextY,
            playerX,
            playerY
        )
    ) {
        return true
    }

    // =========================
    // BOT 1
    // =========================

    if (
        botId != 1 &&
        bot1.health > 0 &&
        isCarCollision(
            nextX,
            nextY,
            bot1.x,
            bot1.y
        )
    ) {
        return true
    }

    // =========================
    // BOT 2
    // =========================

    if (
        botId != 2 &&
        bot2.health > 0 &&
        isCarCollision(
            nextX,
            nextY,
            bot2.x,
            bot2.y
        )
    ) {
        return true
    }

    // =========================
    // BOT 3
    // =========================

    if (
        botId != 3 &&
        bot3.health > 0 &&
        isCarCollision(
            nextX,
            nextY,
            bot3.x,
            bot3.y
        )
    ) {
        return true
    }

    return false
}