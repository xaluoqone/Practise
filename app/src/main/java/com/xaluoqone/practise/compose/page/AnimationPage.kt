package com.xaluoqone.practise.compose.page

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import kotlinx.coroutines.launch

@Composable
fun AnimationPage() {
    val coroutineScope = rememberCoroutineScope()
    val anim = remember { Animatable(0f) }
    val decay = remember { exponentialDecay<Float>(.1f, 1f) }

    Box(Modifier.fillMaxSize()) {
        Button(
            onClick = {
                coroutineScope.launch {
                    //anim.animateTo(300.dp, tween(2000, easing = FastOutLinearInEasing))
                    //anim.animateTo(300.dp, snap(1000))
                    /*anim.animateTo(300.dp, keyframes {
                        durationMillis = 4000
                        80.dp at 1000 with LinearOutSlowInEasing
                        300.dp at 2000 with FastOutLinearInEasing
                        120.dp at 3000 with FastOutSlowInEasing
                    })*/
                    /*anim.animateTo(
                        anim.value + (10f * 360 + (1..360).random()),
                        tween(10000, easing = CubicBezierEasing(0f, .1f, 0f, .92f))
                    )*/
                    //anim.updateBounds(upperBound = 6000f)
                    anim.animateDecay((10..20).random() * 360f, decay)
                }
            },
            Modifier
                .rotate(anim.value)
                .align(Alignment.Center)
        ) {
            Text("溜了溜了")
        }
    }
}