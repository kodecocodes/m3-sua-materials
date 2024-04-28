package com.kodeco.android.devscribe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kodeco.android.devscribe.ui.views.CreateNoteScreen
import com.kodeco.android.devscribe.ui.views.MainScreen

@Composable
fun DevScribeNavHost(
    navController: NavHostController
) {
  NavHost(navController = navController, startDestination = Screens.Notes.route) {
    composable(Screens.Notes.route) {
      MainScreen(
        navigateToCreateNote = {
        navController.navigate(Screens.CreateNote.route)
      }
      )
    }
    composable(Screens.CreateNote.route) {
      CreateNoteScreen(
        navigateBack = {
        navController.popBackStack()
      }
      )
    }
  }
}