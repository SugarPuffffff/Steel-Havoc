package com.sadi.steelhavoc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun GameScreen() {

    var matchId by remember {
        mutableIntStateOf(0)
    }

    // =========================
    // PLAYER
    // =========================

    var playerX by remember(matchId) {
        mutableFloatStateOf(0f)
    }

    var playerY by remember(matchId) {
        mutableFloatStateOf(0f)
    }

    var rotation by remember(matchId) {
        mutableFloatStateOf(0f)
    }

    var playerHealth by remember(matchId) {
        mutableIntStateOf(100)
    }

    // =========================
    // BOTS
    // =========================

    var bot1 by remember(matchId) {
        mutableStateOf(
            BotCarState(
                id = 1,
                x = -260f,
                y = -320f,
                rotation = 180f,
                health = 100
            )
        )
    }

    var bot2 by remember(matchId) {
        mutableStateOf(
            BotCarState(
                id = 2,
                x = 300f,
                y = -160f,
                rotation = 225f,
                health = 100
            )
        )
    }

    var bot3 by remember(matchId) {
        mutableStateOf(
            BotCarState(
                id = 3,
                x = 250f,
                y = 360f,
                rotation = 0f,
                health = 100
            )
        )
    }

    // =========================
    // BULLETS
    // =========================

    var bullets by remember(matchId) {
        mutableStateOf(
            emptyList<BulletState>()
        )
    }

    var nextBulletId by remember(matchId) {
        mutableIntStateOf(1)
    }

    // =========================
    // BOT FIRE COOLDOWN
    // =========================

    var bot1LastFire by remember(matchId) {
        mutableLongStateOf(0L)
    }

    var bot2LastFire by remember(matchId) {
        mutableLongStateOf(0L)
    }

    var bot3LastFire by remember(matchId) {
        mutableLongStateOf(0L)
    }

    val botFireCooldown = 900L

    // =========================
    // EXPLOSIONS
    // =========================

    var playerExplosion by remember(matchId) {
        mutableStateOf(false)
    }

    var bot1Explosion by remember(matchId) {
        mutableStateOf(false)
    }

    var bot2Explosion by remember(matchId) {
        mutableStateOf(false)
    }

    var bot3Explosion by remember(matchId) {
        mutableStateOf(false)
    }

    var playerExplosionFinished by remember(matchId) {
        mutableStateOf(false)
    }

    // =========================
    // SETTINGS
    // =========================

    val moveSpeed = 4f
    val turnSpeed = 3f

    val maxX = 520f
    val maxY = 720f

    // =========================
    // MATCH RESULT
    // =========================

    val playerWon =
        playerHealth > 0 &&
                bot1.health <= 0 &&
                bot2.health <= 0 &&
                bot3.health <= 0

    val playerLost =
        playerHealth <= 0

    val showResult =
        playerWon ||
                (
                        playerLost &&
                                playerExplosionFinished
                        )

    // =========================
    // EXPLOSION DETECTION
    // + EXPLOSION SOUND
    // =========================

    LaunchedEffect(
        playerHealth,
        bot1.health,
        bot2.health,
        bot3.health
    ) {

        if (
            playerHealth <= 0 &&
            !playerExplosion
        ) {
            playerExplosion = true

            // 💥 PLAYER EXPLOSION SOUND
            SoundManager.playExplosion()
        }

        if (
            bot1.health <= 0 &&
            !bot1Explosion
        ) {
            bot1Explosion = true

            // 💥 BOT 1 EXPLOSION SOUND
            SoundManager.playExplosion()
        }

        if (
            bot2.health <= 0 &&
            !bot2Explosion
        ) {
            bot2Explosion = true

            // 💥 BOT 2 EXPLOSION SOUND
            SoundManager.playExplosion()
        }

        if (
            bot3.health <= 0 &&
            !bot3Explosion
        ) {
            bot3Explosion = true

            // 💥 BOT 3 EXPLOSION SOUND
            SoundManager.playExplosion()
        }
    }

    // =========================
    // BOT MOVEMENT LOOP
    // =========================

    LaunchedEffect(matchId) {

        while (true) {

            if (
                playerHealth > 0 &&
                !playerWon
            ) {

                val targets =
                    mutableListOf<
                            Triple<Int, Float, Float>
                            >()

                if (playerHealth > 0) {
                    targets.add(
                        Triple(
                            0,
                            playerX,
                            playerY
                        )
                    )
                }

                if (bot1.health > 0) {
                    targets.add(
                        Triple(
                            1,
                            bot1.x,
                            bot1.y
                        )
                    )
                }

                if (bot2.health > 0) {
                    targets.add(
                        Triple(
                            2,
                            bot2.x,
                            bot2.y
                        )
                    )
                }

                if (bot3.health > 0) {
                    targets.add(
                        Triple(
                            3,
                            bot3.x,
                            bot3.y
                        )
                    )
                }

                if (bot1.health > 0) {

                    bot1 = updateBot(
                        bot = bot1,
                        targets = targets,
                        maxX = maxX,
                        maxY = maxY,

                        playerX = playerX,
                        playerY = playerY,
                        playerHealth = playerHealth,

                        bot1 = bot1,
                        bot2 = bot2,
                        bot3 = bot3
                    )
                }

                if (bot2.health > 0) {

                    bot2 = updateBot(
                        bot = bot2,
                        targets = targets,
                        maxX = maxX,
                        maxY = maxY,

                        playerX = playerX,
                        playerY = playerY,
                        playerHealth = playerHealth,

                        bot1 = bot1,
                        bot2 = bot2,
                        bot3 = bot3
                    )
                }

                if (bot3.health > 0) {

                    bot3 = updateBot(
                        bot = bot3,
                        targets = targets,
                        maxX = maxX,
                        maxY = maxY,

                        playerX = playerX,
                        playerY = playerY,
                        playerHealth = playerHealth,

                        bot1 = bot1,
                        bot2 = bot2,
                        bot3 = bot3
                    )
                }
            }

            delay(30)
        }
    }

    // =========================
    // BOT FIRE LOOP
    // =========================

    LaunchedEffect(matchId) {

        while (true) {

            if (
                playerHealth > 0 &&
                !playerWon
            ) {

                val now =
                    System.currentTimeMillis()

                val targets =
                    mutableListOf<CombatTarget>()

                if (playerHealth > 0) {
                    targets.add(
                        CombatTarget(
                            id = 0,
                            x = playerX,
                            y = playerY,
                            health = playerHealth
                        )
                    )
                }

                if (bot1.health > 0) {
                    targets.add(
                        CombatTarget(
                            id = 1,
                            x = bot1.x,
                            y = bot1.y,
                            health = bot1.health
                        )
                    )
                }

                if (bot2.health > 0) {
                    targets.add(
                        CombatTarget(
                            id = 2,
                            x = bot2.x,
                            y = bot2.y,
                            health = bot2.health
                        )
                    )
                }

                if (bot3.health > 0) {
                    targets.add(
                        CombatTarget(
                            id = 3,
                            x = bot3.x,
                            y = bot3.y,
                            health = bot3.health
                        )
                    )
                }

                // =====================
                // BOT 1 FIRE
                // =====================

                if (
                    bot1.health > 0 &&
                    now - bot1LastFire >=
                    botFireCooldown
                ) {

                    val result =
                        tryBotFire(
                            bot = bot1,
                            targets = targets,
                            bulletId = nextBulletId
                        )

                    if (result.bullet != null) {

                        bullets =
                            bullets +
                                    result.bullet

                        nextBulletId++

                        bot1LastFire = now

                        // 🔫 BOT FIRE SOUND
                        SoundManager.playGunFire()
                    }
                }

                // =====================
                // BOT 2 FIRE
                // =====================

                if (
                    bot2.health > 0 &&
                    now - bot2LastFire >=
                    botFireCooldown
                ) {

                    val result =
                        tryBotFire(
                            bot = bot2,
                            targets = targets,
                            bulletId = nextBulletId
                        )

                    if (result.bullet != null) {

                        bullets =
                            bullets +
                                    result.bullet

                        nextBulletId++

                        bot2LastFire = now

                        // 🔫 BOT FIRE SOUND
                        SoundManager.playGunFire()
                    }
                }

                // =====================
                // BOT 3 FIRE
                // =====================

                if (
                    bot3.health > 0 &&
                    now - bot3LastFire >=
                    botFireCooldown
                ) {

                    val result =
                        tryBotFire(
                            bot = bot3,
                            targets = targets,
                            bulletId = nextBulletId
                        )

                    if (result.bullet != null) {

                        bullets =
                            bullets +
                                    result.bullet

                        nextBulletId++

                        bot3LastFire = now

                        // 🔫 BOT FIRE SOUND
                        SoundManager.playGunFire()
                    }
                }
            }

            delay(100)
        }
    }

    // =========================
    // BULLET DAMAGE LOOP
    // =========================

    LaunchedEffect(matchId) {

        while (true) {

            if (
                playerHealth > 0 &&
                !playerWon
            ) {

                val updatedBullets =
                    mutableListOf<BulletState>()

                for (oldBullet in bullets) {

                    val bullet =
                        moveBullet(oldBullet)

                    var hit = false

                    // PLAYER HIT
                    if (
                        playerHealth > 0 &&
                        bulletHitsTarget(
                            bullet,
                            CombatTarget(
                                id = 0,
                                x = playerX,
                                y = playerY,
                                health = playerHealth
                            )
                        )
                    ) {

                        playerHealth =
                            applyDamage(
                                playerHealth,
                                bullet.damage
                            )

                        hit = true
                    }

                    // BOT 1 HIT
                    if (
                        !hit &&
                        bot1.health > 0 &&
                        bulletHitsTarget(
                            bullet,
                            CombatTarget(
                                id = 1,
                                x = bot1.x,
                                y = bot1.y,
                                health = bot1.health
                            )
                        )
                    ) {

                        bot1 =
                            bot1.copy(
                                health =
                                    applyDamage(
                                        bot1.health,
                                        bullet.damage
                                    )
                            )

                        hit = true
                    }

                    // BOT 2 HIT
                    if (
                        !hit &&
                        bot2.health > 0 &&
                        bulletHitsTarget(
                            bullet,
                            CombatTarget(
                                id = 2,
                                x = bot2.x,
                                y = bot2.y,
                                health = bot2.health
                            )
                        )
                    ) {

                        bot2 =
                            bot2.copy(
                                health =
                                    applyDamage(
                                        bot2.health,
                                        bullet.damage
                                    )
                            )

                        hit = true
                    }

                    // BOT 3 HIT
                    if (
                        !hit &&
                        bot3.health > 0 &&
                        bulletHitsTarget(
                            bullet,
                            CombatTarget(
                                id = 3,
                                x = bot3.x,
                                y = bot3.y,
                                health = bot3.health
                            )
                        )
                    ) {

                        bot3 =
                            bot3.copy(
                                health =
                                    applyDamage(
                                        bot3.health,
                                        bullet.damage
                                    )
                            )

                        hit = true
                    }

                    if (
                        !hit &&
                        !bulletOutsideWorld(
                            bullet = bullet,
                            maxX = 700f,
                            maxY = 900f
                        )
                    ) {

                        updatedBullets.add(
                            bullet
                        )
                    }
                }

                bullets =
                    updatedBullets
            }

            delay(16)
        }
    }

    // =========================
    // GAME UI
    // =========================

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF202020)
            )
    ) {

        Box(
            modifier =
                Modifier.fillMaxSize(),

            contentAlignment =
                Alignment.Center
        ) {

            CombatMap(
                cameraX = playerX,
                cameraY = playerY
            )

            if (bot1.health > 0) {
                EnemyCar(
                    bot = bot1,
                    cameraX = playerX,
                    cameraY = playerY
                )
            }

            if (bot2.health > 0) {
                EnemyCar(
                    bot = bot2,
                    cameraX = playerX,
                    cameraY = playerY
                )
            }

            if (bot3.health > 0) {
                EnemyCar(
                    bot = bot3,
                    cameraX = playerX,
                    cameraY = playerY
                )
            }

            // BULLETS
            bullets.forEach { bullet ->

                BulletView(
                    bullet = bullet,
                    cameraX = playerX,
                    cameraY = playerY
                )
            }

            // BOT 1 EXPLOSION
            if (bot1Explosion) {

                ExplosionEffect(
                    worldX = bot1.x,
                    worldY = bot1.y,
                    cameraX = playerX,
                    cameraY = playerY,
                    onFinished = {
                        bot1Explosion = false
                    }
                )
            }

            // BOT 2 EXPLOSION
            if (bot2Explosion) {

                ExplosionEffect(
                    worldX = bot2.x,
                    worldY = bot2.y,
                    cameraX = playerX,
                    cameraY = playerY,
                    onFinished = {
                        bot2Explosion = false
                    }
                )
            }

            // BOT 3 EXPLOSION
            if (bot3Explosion) {

                ExplosionEffect(
                    worldX = bot3.x,
                    worldY = bot3.y,
                    cameraX = playerX,
                    cameraY = playerY,
                    onFinished = {
                        bot3Explosion = false
                    }
                )
            }
        }

        // =========================
        // PLAYER CAR
        // =========================

        if (playerHealth > 0) {

            Image(
                painter =
                    painterResource(
                        id =
                            R.drawable.player_car
                    ),

                contentDescription =
                    "Player Combat Car",

                modifier =
                    Modifier
                        .align(
                            Alignment.Center
                        )
                        .size(
                            width = 110.dp,
                            height = 170.dp
                        )
                        .graphicsLayer {
                            rotationZ =
                                rotation
                        },

                contentScale =
                    ContentScale.Fit
            )
        }

        // =========================
        // PLAYER EXPLOSION
        // =========================

        if (playerExplosion) {

            ExplosionEffect(
                worldX = playerX,
                worldY = playerY,
                cameraX = playerX,
                cameraY = playerY,
                onFinished = {

                    playerExplosion = false

                    playerExplosionFinished =
                        true
                }
            )
        }

        // =========================
        // PLAYER HP
        // =========================

        Text(
            text = "HP: $playerHealth",

            color =
                if (playerHealth > 40) {
                    Color.Green
                } else {
                    Color.Red
                },

            fontSize = 20.sp,

            modifier =
                Modifier
                    .align(
                        Alignment.TopStart
                    )
                    .offset(
                        x = 20.dp,
                        y = 25.dp
                    )
        )

        // =========================
        // BOT HP
        // =========================

        Text(
            text =
                "B1:${bot1.health}  " +
                        "B2:${bot2.health}  " +
                        "B3:${bot3.health}",

            color = Color.White,

            fontSize = 13.sp,

            modifier =
                Modifier
                    .align(
                        Alignment.TopCenter
                    )
                    .offset(
                        y = 55.dp
                    )
        )

        // =========================
        // PLAYER CONTROLS
        // =========================

        if (
            playerHealth > 0 &&
            !playerWon
        ) {

            // =====================
            // FORWARD
            // =====================

            HoldControlButton(
                text = "↑",

                modifier =
                    Modifier
                        .align(
                            Alignment.BottomStart
                        )
                        .offset(
                            x = 30.dp,
                            y = (-145).dp
                        ),

                onHold = {

                    val angle =
                        Math.toRadians(
                            rotation.toDouble()
                        )

                    val nextX =
                        playerX +
                                (
                                        sin(angle) *
                                                moveSpeed
                                        ).toFloat()

                    val nextY =
                        playerY -
                                (
                                        cos(angle) *
                                                moveSpeed
                                        ).toFloat()

                    if (
                        nextX in -maxX..maxX &&
                        nextY in -maxY..maxY &&
                        !isPositionBlocked(
                            nextX,
                            nextY
                        ) &&
                        !isPlayerBlockedByBots(
                            playerX = nextX,
                            playerY = nextY,
                            bot1 = bot1,
                            bot2 = bot2,
                            bot3 = bot3
                        )
                    ) {

                        playerX = nextX
                        playerY = nextY
                    }
                }
            )

            // =====================
            // REVERSE
            // =====================

            HoldControlButton(
                text = "↓",

                modifier =
                    Modifier
                        .align(
                            Alignment.BottomStart
                        )
                        .offset(
                            x = 30.dp,
                            y = (-65).dp
                        ),

                onHold = {

                    val angle =
                        Math.toRadians(
                            rotation.toDouble()
                        )

                    val nextX =
                        playerX -
                                (
                                        sin(angle) *
                                                moveSpeed
                                        ).toFloat()

                    val nextY =
                        playerY +
                                (
                                        cos(angle) *
                                                moveSpeed
                                        ).toFloat()

                    if (
                        nextX in -maxX..maxX &&
                        nextY in -maxY..maxY &&
                        !isPositionBlocked(
                            nextX,
                            nextY
                        ) &&
                        !isPlayerBlockedByBots(
                            playerX = nextX,
                            playerY = nextY,
                            bot1 = bot1,
                            bot2 = bot2,
                            bot3 = bot3
                        )
                    ) {

                        playerX = nextX
                        playerY = nextY
                    }
                }
            )

            // =====================
            // LEFT
            // =====================

            HoldControlButton(
                text = "←",

                modifier =
                    Modifier
                        .align(
                            Alignment.BottomEnd
                        )
                        .offset(
                            x = (-105).dp,
                            y = (-65).dp
                        ),

                onHold = {

                    rotation -=
                        turnSpeed

                    if (
                        rotation <= -360f
                    ) {
                        rotation += 360f
                    }
                }
            )

            // =====================
            // RIGHT
            // =====================

            HoldControlButton(
                text = "→",

                modifier =
                    Modifier
                        .align(
                            Alignment.BottomEnd
                        )
                        .offset(
                            x = (-25).dp,
                            y = (-65).dp
                        ),

                onHold = {

                    rotation +=
                        turnSpeed

                    if (
                        rotation >= 360f
                    ) {
                        rotation -= 360f
                    }
                }
            )

            // =====================
            // PLAYER FIRE
            // =====================

            Button(
                onClick = {

                    // 🔫 GUN FIRE SOUND
                    SoundManager.playGunFire()

                    val angle =
                        Math.toRadians(
                            rotation.toDouble()
                        )

                    val muzzleDistance =
                        90f

                    val bulletX =
                        playerX +
                                (
                                        sin(angle) *
                                                muzzleDistance
                                        ).toFloat()

                    val bulletY =
                        playerY -
                                (
                                        cos(angle) *
                                                muzzleDistance
                                        ).toFloat()

                    bullets =
                        bullets +
                                createBullet(
                                    id =
                                        nextBulletId,

                                    ownerId = 0,

                                    startX =
                                        bulletX,

                                    startY =
                                        bulletY,

                                    rotation =
                                        rotation
                                )

                    nextBulletId++
                },

                modifier =
                    Modifier
                        .align(
                            Alignment.BottomEnd
                        )
                        .offset(
                            x = (-35).dp,
                            y = (-150).dp
                        )
                        .size(
                            width = 95.dp,
                            height = 60.dp
                        )
            ) {

                Text(
                    text = "FIRE",
                    fontSize = 16.sp
                )
            }
        }

        // =========================
        // GAME RESULT
        // =========================

        if (showResult) {

            GameResultScreen(
                playerWon = playerWon,

                onRestart = {
                    matchId++
                }
            )
        }
    }
}


// ==========================================
// BOT AI + COLLISION
// ==========================================

fun updateBot(
    bot: BotCarState,
    targets:
    List<Triple<Int, Float, Float>>,
    maxX: Float,
    maxY: Float,

    playerX: Float,
    playerY: Float,
    playerHealth: Int,

    bot1: BotCarState,
    bot2: BotCarState,
    bot3: BotCarState
): BotCarState {

    if (bot.health <= 0) {
        return bot
    }

    val possibleTargets =
        targets.filter {
            it.first != bot.id
        }

    if (possibleTargets.isEmpty()) {
        return bot
    }

    val nearestTarget =
        possibleTargets.minByOrNull {

            val dx =
                it.second - bot.x

            val dy =
                it.third - bot.y

            sqrt(
                dx * dx +
                        dy * dy
            )

        } ?: return bot

    val targetX =
        nearestTarget.second

    val targetY =
        nearestTarget.third

    val dx =
        targetX - bot.x

    val dy =
        targetY - bot.y

    val targetRotation =
        Math.toDegrees(
            atan2(
                dx.toDouble(),
                (-dy).toDouble()
            )
        ).toFloat()

    var newRotation =
        bot.rotation

    var difference =
        targetRotation -
                newRotation

    while (
        difference > 180f
    ) {
        difference -= 360f
    }

    while (
        difference < -180f
    ) {
        difference += 360f
    }

    val botTurnSpeed =
        2.2f

    if (
        difference > 0f
    ) {

        newRotation +=
            botTurnSpeed

    } else {

        newRotation -=
            botTurnSpeed
    }

    val botMoveSpeed =
        1.8f

    val angle =
        Math.toRadians(
            newRotation.toDouble()
        )

    val nextX =
        bot.x +
                (
                        sin(angle) *
                                botMoveSpeed
                        ).toFloat()

    val nextY =
        bot.y -
                (
                        cos(angle) *
                                botMoveSpeed
                        ).toFloat()

    val blockedByCar =
        isBotBlockedByCars(
            botId = bot.id,

            nextX = nextX,
            nextY = nextY,

            playerX = playerX,
            playerY = playerY,

            playerHealth =
                playerHealth,

            bot1 = bot1,
            bot2 = bot2,
            bot3 = bot3
        )

    val canMove =
        nextX in -maxX..maxX &&
                nextY in -maxY..maxY &&
                !isPositionBlocked(
                    nextX,
                    nextY
                ) &&
                !blockedByCar

    return if (canMove) {

        bot.copy(
            x = nextX,
            y = nextY,
            rotation = newRotation
        )

    } else {

        bot.copy(
            rotation =
                newRotation + 12f
        )
    }
}