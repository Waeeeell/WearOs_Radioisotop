package com.example.proyectoreloj.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.proyectoreloj.presentation.navigation.Screen
import com.example.proyectoreloj.presentation.screens.*
import com.example.proyectoreloj.presentation.theme.ProyectoRelojTheme
import com.example.proyectoreloj.presentation.viewmodel.MotivationalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoRelojTheme {
                WearApp()
            }
        }
    }
}

@Composable
fun WearApp() {
    val navController = rememberSwipeDismissableNavController()

    // Shared ViewModel — both Home and Activity screens read from the same instance
    val motivationalViewModel: MotivationalViewModel = viewModel()

    SwipeDismissableNavHost(
        navController = navController,
        startDestination = Screen.HOME
    ) {
        composable(Screen.HOME) {
            HomeScreen(
                motivationalViewModel = motivationalViewModel,
                onNavigateToActivity = {
                    navController.navigate(Screen.ACTIVITY)
                },
                onNavigateToSos = {
                    navController.navigate(Screen.SOS)
                },
                onNavigateToEcg = {
                    navController.navigate(Screen.ECG_INTRO)
                }
            )
        }

        composable(Screen.ACTIVITY) {
            ActivityScreen(
                motivationalViewModel = motivationalViewModel
            )
        }

        composable(Screen.SOS) {
            SosScreen(
                onDismiss = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ECG_INTRO) {
            EcgIntroScreen(
                onStartEcg = {
                    navController.navigate(Screen.ECG_RECORDING)
                }
            )
        }

        composable(Screen.ECG_RECORDING) {
            EcgRecordingScreen(
                onFinished = {
                    navController.popBackStack(Screen.HOME, inclusive = false)
                }
            )
        }
    }
}
