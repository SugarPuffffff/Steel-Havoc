package com.sadi.steelhavoc

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun HoldControlButton(
    text: String,
    modifier: Modifier = Modifier,
    onHold: () -> Unit
) {

    var pressed by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(pressed) {

        while (pressed) {

            onHold()

            delay(16)
        }
    }

    Box(
        modifier = modifier
            .size(70.dp)
            .background(
                color = if (pressed) {
                    Color(0xFFB71C1C)
                } else {
                    Color(0xFF333333)
                },
                shape = CircleShape
            )
            .pointerInput(Unit) {

                detectTapGestures(
                    onPress = {

                        pressed = true

                        try {
                            tryAwaitRelease()
                        } finally {
                            pressed = false
                        }
                    }
                )
            },

        contentAlignment = Alignment.Center
    ) {

        Text(
            text = text,
            color = Color.White,
            fontSize = 30.sp
        )
    }
}