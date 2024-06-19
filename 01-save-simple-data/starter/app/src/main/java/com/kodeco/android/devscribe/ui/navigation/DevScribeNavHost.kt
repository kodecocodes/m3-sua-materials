package com.kodeco.android.devscribe.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kodeco.android.devscribe.ui.views.CreateNoteScreen
import com.kodeco.android.devscribe.ui.views.EditNoteScreen
import com.kodeco.android.devscribe.ui.views.MainScreen
import com.kodeco.android.devscribe.ui.views.NoteDetailsScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

@Composable
fun DevScribeNavHost(
    navController: NavHostController
) {
  NavHost(navController = navController, startDestination = Screens.Notes.route) {
    composable(Screens.Notes.route) {
      MainScreen(
        navigateToCreateNote = {
        navController.navigate(Screens.CreateNote.route)
      },
        navigateToNoteDetails = { note ->
        navController.navigate("${Screens.NoteDetails.route}/${Json.encodeToString(note)}")
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

    composable(Screens.NoteDetails.route+"/{note}") { backStackEntry ->
      NoteDetailsScreen(
        navigateBack = {
        navController.popBackStack()
      },
        note = backStackEntry.arguments?.getString("note")?.let { decodeFromString(it) },
        editNote = {
          navController.navigate("${Screens.EditNote.route}/${Json.encodeToString(it)}")
        }
      )
    }

    composable(Screens.EditNote.route+"/{note}") { backStackEntry ->
      EditNoteScreen(
        navigateToHome = {
          navController.navigate(Screens.Notes.route)
        },
        note = backStackEntry.arguments?.getString("note")?.let { decodeFromString(it) }
      )
    }
  }
}