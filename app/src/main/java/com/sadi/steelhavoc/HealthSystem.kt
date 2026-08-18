package com.sadi.steelhavoc

data class PlayerHealthState(
    val health: Int = 100,
    val alive: Boolean = true
)

fun applyDamage(
    currentHealth: Int,
    damage: Int
): Int {

    val newHealth = currentHealth - damage

    return if (newHealth < 0) {
        0
    } else {
        newHealth
    }
}

fun isDestroyed(
    health: Int
): Boolean {
    return health <= 0
}