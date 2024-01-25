package com.xaluoqone.practise.compose.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xaluoqone.practise.compose.page.AnimatedVisibilityPage
import com.xaluoqone.practise.compose.page.AnimationPage
import com.xaluoqone.practise.compose.page.CustomPage
import com.xaluoqone.practise.compose.page.MainPage
import com.xaluoqone.practise.compose.page.SideEffectPage
import com.xaluoqone.practise.compose.page.SimpleUsagePage
import com.xaluoqone.practise.compose.page.TransitionPage

@Composable
fun AppNav() {
    val navController = rememberNavController()
    NavHost(navController, NavRoute.Main.name) {
        composable(NavRoute.Main.name) {
            MainPage {
                navController.navigate(it.name)
            }
        }
        composable(NavRoute.Usage.name) {
            SimpleUsagePage()
        }
        composable(NavRoute.Custom.name) {
            CustomPage()
        }
        composable(NavRoute.Animation.name) {
            AnimationPage()
        }
        composable(NavRoute.Transition.name) {
            TransitionPage()
        }
        composable(NavRoute.AnimatedVisibility.name) {
            AnimatedVisibilityPage()
        }
        composable(NavRoute.SideEffect.name) {
            SideEffectPage()
        }
    }
}