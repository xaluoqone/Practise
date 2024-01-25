package com.xaluoqone.practise.compose.page

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.xaluoqone.practise.compose.LocalStatusBarHeight

@Composable
fun SideEffectPage() {
    SideEffect {
        Log.v("xaluoqone", "SideEffect")
    }

    var shown by remember {
        mutableStateOf(false)
    }

    val statusBarHeight = LocalStatusBarHeight.current

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = statusBarHeight)
    ) {
        Button(onClick = { shown = !shown }) {
            Text(text = "click me to change state")
        }
        if (shown) {
            DisposableEffect(Unit) {
                Log.v("xaluoqone", "DisposableEffect Start")
                onDispose {
                    Log.v("xaluoqone", "DisposableEffect End")
                }
            }
            Text(text = "副作用")
        }
    }
}