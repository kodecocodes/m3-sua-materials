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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kodeco.android.devscribe.R
import com.kodeco.android.devscribe.data.local.NoteEntity
import com.kodeco.android.devscribe.ui.components.SpinnerView
import com.kodeco.android.devscribe.ui.components.UpdateNoteLocationView
import com.kodeco.android.devscribe.ui.state.CreateNoteState
import com.kodeco.android.devscribe.ui.viewmodels.MainViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    navigateToHome: () -> Unit,
    note: NoteEntity?
) {
    val viewModel: MainViewModel = koinInject()
    LaunchedEffect(Unit) {
        note?.let {
            // TODO: update state with previous note details
        }
    }
    val createNoteState by viewModel.createNoteState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_edit_note)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = navigateToHome,
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
                // TODO: add update note screen content
            }
        }
    )
}

@Composable
fun UpdateNoteScreenContent(
    createNoteState: CreateNoteState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (String) -> Unit,
    onUpdateNote: () -> Unit,
    onNoteLocationChange: (String) -> Unit
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
            onPrioritySelected = onPriorityChange,
            previousSelectedItem = createNoteState.priority ?: "",
            isEditNote = true
        )

        UpdateNoteLocationView(
            onNoteLocationChange = onNoteLocationChange,
            previousOption = createNoteState.noteLocation ?: ""
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onUpdateNote,
            enabled = createNoteState.isValid(),
            shape = RoundedCornerShape(10.dp),
            content = {
                Text("Update Note")
            }
        )
    }
}