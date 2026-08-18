package com.sadi.steelhavoc

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class BulletState(
    val id: Int,
    val ownerId: Int,
    val x: Float,
    val y: Float,
    val rotation: Float,
    val speed: Float = 10f,
    val damage: Int = 20,
    val active: Boolean = true
)

data class CombatTarget(
    val id: Int,
    val x: Float,
    val y: Float,
    val health: Int
)

fun createBullet(
    id: Int,
    ownerId: Int,
    startX: Float,
    startY: Float,
    rotation: Float
): BulletState {

    return BulletState(
        id = id,
        ownerId = ownerId,
        x = startX,
        y = startY,
        rotation = rotation
    )
}

fun moveBullet(
    bullet: BulletState
): BulletState {

    if (!bullet.active) {
        return bullet
    }

    val angle =
        Math.toRadians(
            bullet.rotation.toDouble()
        )

    val nextX =
        bullet.x +
                (
                        sin(angle) *
                                bullet.speed
                        ).toFloat()

    val nextY =
        bullet.y -
                (
                        cos(angle) *
                                bullet.speed
                        ).toFloat()

    return bullet.copy(
        x = nextX,
        y = nextY
    )
}

fun bulletHitsTarget(
    bullet: BulletState,
    target: CombatTarget,
    hitRadius: Float = 55f
): Boolean {

    if (!bullet.active) {
        return false
    }

    if (bullet.ownerId == target.id) {
        return false
    }

    if (target.health <= 0) {
        return false
    }

    val dx =
        bullet.x - target.x

    val dy =
        bullet.y - target.y

    val distance =
        sqrt(
            dx * dx +
                    dy * dy
        )

    return distance <= hitRadius
}

fun bulletOutsideWorld(
    bullet: BulletState,
    maxX: Float,
    maxY: Float
): Boolean {

    return (
            bullet.x < -maxX ||
                    bullet.x > maxX ||
                    bullet.y < -maxY ||
                    bullet.y > maxY
            )
}