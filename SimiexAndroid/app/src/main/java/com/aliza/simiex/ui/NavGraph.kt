package com.aliza.simiex.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aliza.simiex.ui.screens.home.HomeScreen
import com.aliza.simiex.ui.screens.profile.ProfileScreen
import com.aliza.simiex.ui.screens.report.ReportScreen
import com.aliza.simiex.ui.screens.splash.SplashScreen
import com.aliza.simiex.utils.constants.HOME_SCREEN
import com.aliza.simiex.utils.constants.PROFILE_SCREEN
import com.aliza.simiex.utils.constants.REPORT_SCREEN
import com.aliza.simiex.utils.constants.SPLASH_SCREEN

@Composable
fun NavGraph(
    navController: NavHostController,
    constantTopPadding: Dp,
    constantBottomPadding: Dp,
) {
    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN,
        enterTransition = {
            fadeIn(
                animationSpec = tween(1000)
            )

        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(1000)
            )
        }
    ) {
        composable(SPLASH_SCREEN) {
            SplashScreen {
                navController.popBackStack()
                navController.navigate(it)
            }
        }
        composable(SPLASH_SCREEN) {
            SplashScreen {
                navController.popBackStack()
                navController.navigate(it)
            }
        }
        composable(HOME_SCREEN) {
            HomeScreen(
                constantTopPadding = constantTopPadding,
                constantBottomPadding = constantBottomPadding
            )
        }
        composable(REPORT_SCREEN) {
            ReportScreen(
                constantTopPadding = constantTopPadding,
                constantBottomPadding = constantBottomPadding
            )
        }
        composable(PROFILE_SCREEN) {
            ProfileScreen(
                constantTopPadding = constantTopPadding,
                constantBottomPadding = constantBottomPadding
            )
        }
    }
}
