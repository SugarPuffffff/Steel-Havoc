package com.sadi.steelhavoc

import kotlin.math.sqrt

data class BotFireResult(
    val bullet: BulletState?,
    val targetId: Int?
)

fun tryBotFire(
    bot: BotCarState,
    targets: List<CombatTarget>,
    bulletId: Int,
    fireRange: Float = 420f
): BotFireResult {

    if (bot.health <= 0) {
        return BotFireResult(
            bullet = null,
            targetId = null
        )
    }

    val possibleTargets =
        targets.filter {
            it.id != bot.id &&
                    it.health > 0
        }

    if (possibleTargets.isEmpty()) {
        return BotFireResult(
            bullet = null,
            targetId = null
        )
    }

    val nearestTarget =
        possibleTargets.minByOrNull {

            val dx = it.x - bot.x
            val dy = it.y - bot.y

            sqrt(
                dx * dx +
                        dy * dy
            )
        } ?: return BotFireResult(
            bullet = null,
            targetId = null
        )

    val dx =
        nearestTarget.x - bot.x

    val dy =
        nearestTarget.y - bot.y

    val distance =
        sqrt(
            dx * dx +
                    dy * dy
        )

    if (distance > fireRange) {
        return BotFireResult(
            bullet = null,
            targetId = nearestTarget.id
        )
    }

    val muzzleDistance = 80f

    val angle =
        Math.toRadians(
            bot.rotation.toDouble()
        )

    val bulletX =
        bot.x +
                (
                        kotlin.math.sin(angle) *
                                muzzleDistance
                        ).toFloat()

    val bulletY =
        bot.y -
                (
                        kotlin.math.cos(angle) *
                                muzzleDistance
                        ).toFloat()

    val bullet =
        createBullet(
            id = bulletId,
            ownerId = bot.id,
            startX = bulletX,
            startY = bulletY,
            rotation = bot.rotation
        )

    return BotFireResult(
        bullet = bullet,
        targetId = nearestTarget.id
    )
}