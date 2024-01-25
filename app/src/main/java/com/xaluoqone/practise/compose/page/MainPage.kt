package com.xaluoqone.practise.compose.page

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xaluoqone.practise.compose.LocalStatusBarHeight
import com.xaluoqone.practise.compose.nav.NavRoute

val chapters = listOf(
    NavRoute.Usage to "Compose简单用法",
    NavRoute.Custom to "自定义Composable",
    NavRoute.Animation to "Compose动画",
    NavRoute.Transition to "Compose Transition 动画",
    NavRoute.AnimatedVisibility to "AnimatedVisibility",
    NavRoute.SideEffect to "SideEffect",
)

@Composable
fun MainPage(navTo: (NavRoute) -> Unit) {
    LazyColumn(Modifier.padding(top = LocalStatusBarHeight.current)) {
        items(chapters) {
            TextButton(
                onClick = {
                    navTo(it.first)
                },
                Modifier
                    .padding(horizontal = 6.dp)
                    .fillMaxWidth()
            ) {
                Text(it.second)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    MainPage {}
}