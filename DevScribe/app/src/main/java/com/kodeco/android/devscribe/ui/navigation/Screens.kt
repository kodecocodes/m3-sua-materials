package com.kodeco.android.devscribe.ui.navigation

sealed class Screens(val route: String) {
    data object Notes : Screens("notes")
    data object CreateNote : Screens("create_note")
}