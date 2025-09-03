package com.subhajeet.weddingplanner.ui.theme.screen.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.subhajeet.weddingplanner.ui.theme.screen.TabScreen
import com.subhajeet.weddingplanner.ui.theme.screen.UpdataChecklistScreen
import com.subhajeet.weddingplanner.ui.theme.screen.WeddingChecklistScreen

@Composable
fun NavApp() {

    val navController = rememberNavController()

    NavHost(
        navController=navController,
        startDestination = Routes.TabScreen
    ){
        composable<Routes.TabScreen> {
            TabScreen(navController = navController) // ✅ Pass navController to tab screen
        }

        composable<Routes.WeddingCheckListRoute> {
            WeddingChecklistScreen(navController=navController)
        }

        composable<Routes.UpdataChecklistRoute> {
            val data = it.toRoute<Routes.UpdataChecklistRoute>()
            UpdataChecklistScreen(navController=navController,
                title=data.title,id =data.id,isCompleted = data.isCompleted)
        }
    }
}