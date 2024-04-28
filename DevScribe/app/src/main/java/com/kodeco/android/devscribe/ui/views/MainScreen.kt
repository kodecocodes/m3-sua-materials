package com.kodeco.android.devscribe.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kodeco.android.devscribe.R
import com.kodeco.android.devscribe.data.local.NoteEntity
import com.kodeco.android.devscribe.di.appModules
import com.kodeco.android.devscribe.ui.components.NotesView
import com.kodeco.android.devscribe.ui.theme.DevScribeTheme
import com.kodeco.android.devscribe.ui.viewmodels.MainViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navigateToCreateNote: () -> Unit){
  val viewModel:MainViewModel = koinInject()
  val noteSource by viewModel.noteSource.collectAsStateWithLifecycle()
  val notes by viewModel.notes.collectAsStateWithLifecycle()
  Scaffold(
    topBar = {
     TopAppBar(
         title = {
           Text(stringResource(id = R.string.app_name))
       },
       colors = TopAppBarDefaults.topAppBarColors(
         containerColor = MaterialTheme.colorScheme.primary,
         titleContentColor = MaterialTheme.colorScheme.onPrimary
       ),
       actions = {
         Icon(
           painter = painterResource(id = R.drawable.baseline_filter_list_24),
           contentDescription = "Filter",
           tint = Color.White
         )
       }
     )
    },
    content = {
      MainScreenContent(
        noteSource = noteSource,
        paddingValues = it,
        onNoteSourceChanged = { noteSource ->
          viewModel.saveNoteSource(noteSource)
        },
        notes = notes

      )
    },
    floatingActionButton = {
      ExtendedFloatingActionButton(
        onClick = navigateToCreateNote,
        content = {
          Icon(Icons.Default.Add, contentDescription = "Create Note")
          Text("Create Note")
        }
      )
    }
  )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
  paddingValues: PaddingValues,
  onNoteSourceChanged: (Int) -> Unit,
  noteSource: Int,
  notes: List<NoteEntity>
) {
  Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
  ) {

    val options = listOf(
      stringResource(id = R.string.label_filter_files),
      stringResource(id = R.string.label_filter_room)
    )
    NotesView(
      notes = notes
    )
  }
}



@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
  DevScribeTheme {
    KoinApplication(
      application = {
      modules(appModules)
    }
    ) {
      MainScreen(
        navigateToCreateNote = {}
      )
    }
  }
}