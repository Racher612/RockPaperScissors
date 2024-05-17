package com.example.myapplication.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.features.game.GameScreen
import com.example.myapplication.features.main.MainScreen
import com.example.myapplication.features.wins.HumanWins
import com.example.myapplication.features.wins.RobotWins

@Composable
fun CustomNavGraph(
    navController: NavHostController,
){

    val navigateToScreen : (String) -> Unit = {route ->
        navController.navigate(route){
            navController.graph.startDestinationRoute?.let { start ->
                popUpTo(start) {
                    saveState = true
                    inclusive = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavHost(navController = navController, startDestination = Screen.Main.route){
        composable(Screen.Main.route){
            MainScreen(navigateToScreen)
        }
        composable(
            Screen.Game.route + "/{data}",
            arguments = listOf(navArgument("data") { type = NavType.StringType })
        ){entry ->
            val rounds = entry.arguments?.getString("data")
            GameScreen(
                navigateToScreen,
                rounds = rounds?.toInt() ?: 3
            )
        }
        composable(Screen.RobotVictory.route){
            RobotWins(navigateToScreen)
        }
        composable(Screen.HumanVictory.route){
            HumanWins(navigateToScreen)
        }
    }
}

sealed class Screen(val route: String){
    data object Main : Screen("main")
    data object Game : Screen("game")
    data object RobotVictory : Screen("robot_wins")
    data object HumanVictory : Screen("human_wins")
}