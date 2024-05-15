package com.kodeco.android.devscribe.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kodeco.android.devscribe.R
import com.kodeco.android.devscribe.data.local.NoteEntity
import com.kodeco.android.devscribe.di.appModules
import com.kodeco.android.devscribe.ui.components.MainScreenActionBarActions
import com.kodeco.android.devscribe.ui.components.NotesView
import com.kodeco.android.devscribe.ui.theme.DevScribeTheme
import com.kodeco.android.devscribe.ui.viewmodels.MainViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
  navigateToCreateNote: () -> Unit,
  navigateToNoteDetails: (NoteEntity) -> Unit
){
  val viewModel:MainViewModel = koinInject()
  val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()
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
          MainScreenActionBarActions(
            filter = selectedFilter,
            onFilterChange = {
              viewModel.saveSelectedFilter(it)
            }
          )
       }
     )
    },
    content = {
      MainScreenContent(
        paddingValues = it,
        notes = notes,
        navigateToNoteDetails = navigateToNoteDetails
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

@Composable
fun MainScreenContent(
  paddingValues: PaddingValues,
  notes: List<NoteEntity>,
  navigateToNoteDetails: (NoteEntity) -> Unit
) {
  Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(Color.LightGray.copy(alpha = 0.2f)),
  ) {
    if (notes.isEmpty()) {
      Column(
        modifier = Modifier
          .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "No notes available",
          modifier = Modifier
            .padding(16.dp)
        )
      }
    } else {
      NotesView(
        notes = notes,
        onNoteClick = navigateToNoteDetails
      )
    }
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
        navigateToCreateNote = {},
        navigateToNoteDetails = {}
      )
    }
  }
}