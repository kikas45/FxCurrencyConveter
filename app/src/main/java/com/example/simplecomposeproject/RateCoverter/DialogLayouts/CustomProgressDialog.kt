package com.example.simplecomposeproject.RateCoverter.DialogLayouts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.foundation.shape.RoundedCornerShape

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomProgressDialog(
    onDismissRequest: () -> Unit = {} // optional: whether the dialog is cancelable
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        // Wrap in AnimatedVisibility to get scale in/out effects.
        AnimatedVisibility(
            visible = true,
            enter = scaleIn(animationSpec = tween(durationMillis = 300)),
            exit = scaleOut(animationSpec = tween(durationMillis = 300))
        ) {
            Surface(
                modifier = Modifier
                    .size(80.dp) // smaller container
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
                tonalElevation = 4.dp,
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp), // adjust padding as needed
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(40.dp), // smaller indicator
                        color = Color(0xFF4CAF50)
                    )
                }
            }
        }
    }
}
