package com.kodeco.android.devscribe.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kodeco.android.devscribe.R
import com.kodeco.android.devscribe.ui.components.SpinnerView
import com.kodeco.android.devscribe.ui.state.CreateNoteEvents
import com.kodeco.android.devscribe.ui.state.CreateNoteState
import com.kodeco.android.devscribe.ui.theme.DevScribeTheme
import com.kodeco.android.devscribe.ui.viewmodels.MainViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
  navigateBack: () -> Unit
) {
  val viewModel: MainViewModel = koinInject()
  val createNoteState by viewModel.createNoteState.collectAsStateWithLifecycle()
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
        navigationIcon = {
          IconButton(
            onClick = navigateBack,
            content = {
              Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
              )
            }
          )
        }
      )
    },
    content = {
      Column(
        modifier = Modifier
          .padding(it)
      ) {
        CreateNoteScreenContent(
          createNoteState = createNoteState,
          onTitleChange = { title ->
            viewModel.handleCreateNoteEvents(CreateNoteEvents.TitleChanged(title))
          },
          onDescriptionChange = { description ->
            viewModel.handleCreateNoteEvents(CreateNoteEvents.DescriptionChanged(description))
          },
          onPriorityChange = { priority ->
            viewModel.handleCreateNoteEvents(CreateNoteEvents.PriorityChanged(priority))
          },
          onCreateNote = {
            viewModel.handleCreateNoteEvents(CreateNoteEvents.CreateNote)
            navigateBack()
          }
        )
      }
    }
  )
}

@Composable
fun CreateNoteScreenContent(
  createNoteState: CreateNoteState,
  onTitleChange: (String) -> Unit,
  onDescriptionChange: (String) -> Unit,
  onPriorityChange: (String) -> Unit,
  onCreateNote: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    OutlinedTextField(
      modifier = Modifier
        .fillMaxWidth(),
      value = createNoteState.title ?: "",
      onValueChange = onTitleChange,
      label = { Text("Title") }
    )
    OutlinedTextField(
      modifier = Modifier
        .fillMaxWidth(),
      value = createNoteState.description ?: "",
      onValueChange = onDescriptionChange,
      label = { Text("Description") }
    )
    SpinnerView(
      onPrioritySelected = onPriorityChange
    )

    Button(
      modifier = Modifier
        .fillMaxWidth(),
      onClick = onCreateNote,
      enabled = createNoteState.isValid(),
      shape = RoundedCornerShape(10.dp),
      content = {
        Text("Create Note")
      }
    )
  }
}


@Preview(showBackground = true)
@Composable
fun CreateNoteScreenPreview() {
  DevScribeTheme {
    CreateNoteScreen(
      navigateBack = { }
    )
  }
}