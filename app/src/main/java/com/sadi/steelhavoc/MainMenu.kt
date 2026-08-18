package com.sadi.steelhavoc

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MainMenu(
    onStartGame: () -> Unit
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.steel_havoc_bg
            ),
            contentDescription = "Steel Havoc Main Menu",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // START GAME
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-90).dp)
                .size(
                    width = 210.dp,
                    height = 55.dp
                )
                .clickable {
                    onStartGame()
                }
        )

        // EXIT
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-25).dp)
                .size(
                    width = 210.dp,
                    height = 55.dp
                )
                .clickable {
                    (context as? Activity)?.finish()
                }
        )
    }
}