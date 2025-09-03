package com.subhajeet.weddingplanner.ui.theme.screen.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.subhajeet.weddingplanner.ui.theme.screen.LoginScreen
import com.subhajeet.weddingplanner.ui.theme.screen.SignUpScreen
import com.subhajeet.weddingplanner.ui.theme.screen.TabScreen
import com.subhajeet.weddingplanner.ui.theme.screen.UpdateChecklistScreen
import com.subhajeet.weddingplanner.ui.theme.screen.WeddingChecklistScreen

@Composable
fun NavApp() {

    val navController = rememberNavController()

    NavHost(
        navController=navController,
        startDestination = Routes.LoginScreenRoute
    ){
        composable<Routes.LoginScreenRoute> {
            LoginScreen(navController = navController) // ✅ Pass navController to tab screen
        }

        composable<Routes.SignUpScreenRoute> {
            SignUpScreen(navController=navController)
        }

        composable<Routes.TabScreen> {
            TabScreen(navController = navController)
        }

        composable<Routes.WeddingCheckListRoute> {
            WeddingChecklistScreen(navController=navController)
        }

        composable<Routes.UpdateChecklistRoute> {
            val data = it.toRoute<Routes.UpdateChecklistRoute>()
            UpdateChecklistScreen(navController=navController,
                title=data.title,id =data.id,isCompleted = data.isCompleted)
        }
    }
}