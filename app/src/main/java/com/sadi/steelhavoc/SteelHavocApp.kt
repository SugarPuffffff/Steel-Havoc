package com.sadi.steelhavoc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun SteelHavocApp() {

    val context = LocalContext.current

    var gameStarted by remember {
        mutableStateOf(false)
    }

    // Prepare all audio immediately
    DisposableEffect(Unit) {

        SoundManager.initialize(context)

        onDispose {
            SoundManager.releaseAll()
        }
    }

    // Change music only when screen changes
    LaunchedEffect(gameStarted) {

        if (gameStarted) {

            SoundManager.playBattleMusic()

        } else {

            SoundManager.playMenuMusic()
        }
    }

    if (gameStarted) {

        GameScreen()

    } else {

        MainMenu(
            onStartGame = {
                gameStarted = true
            }
        )
    }
}