package com.xaluoqone.practise.compose.page

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xaluoqone.practise.compose.LocalStatusBarHeight

@Preview(showBackground = true)
@Composable
fun TransitionPage() {
    var go by remember { mutableStateOf(false) }
    val transition = updateTransition(go, "go transition")
    val offsetX by transition.animateDp(
        { spring(.1f, 100f, 1.dp) },
        label = "offsetX"
    ) { if (it) 200.dp else 0.dp }
    val offsetY by transition.animateDp(
        { spring(.1f, 100f, 1.dp) },
        label = "offsetY"
    ) { if (it) 200.dp else 0.dp }

    val text by transition.animateValue(TwoWayConverter(
        convertToVector = {
            AnimationVector1D(0f)
        },
        convertFromVector = {
            it.value.toString()
        }
    ), label = "text") { if (it) "back" else "go" }

    val statusBarHeight = LocalStatusBarHeight.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = statusBarHeight)
    ) {
        TextButton(
            onClick = {
                go = !go
            },
            modifier = Modifier.offset(offsetX, offsetY)
        ) {
            Text(text)
        }
    }
}