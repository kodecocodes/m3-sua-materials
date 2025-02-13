package com.kodeco.android.devscribe.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.devscribe.data.datastore.DataStoreManager
import com.kodeco.android.devscribe.data.files.ExternalNotesFileManager
import com.kodeco.android.devscribe.data.files.InternalNotesFileManager
import com.kodeco.android.devscribe.data.local.NoteEntity
import com.kodeco.android.devscribe.ui.state.CreateNoteEvents
import com.kodeco.android.devscribe.ui.state.CreateNoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
  private val dataStoreManager: DataStoreManager,
  private val internalNotesFileManager: InternalNotesFileManager,
  private val externalNotesFileManager: ExternalNotesFileManager
): ViewModel() {
  private val _selectedFilter = MutableStateFlow("")
  val selectedFilter = _selectedFilter.asStateFlow()

  private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
  val notes = _notes.asStateFlow()

  init {
    fetchSelectedFilter()
    fetchNotes()
  }


  private val _createNoteState = MutableStateFlow(CreateNoteState())
  val createNoteState = _createNoteState.asStateFlow()

     fun saveSelectedFilter(selectedFilter: String) {
       viewModelScope.launch {
         dataStoreManager.saveSelectedFilter(selectedFilter)
       }
    }

    private fun fetchSelectedFilter() {
      viewModelScope.launch {
        dataStoreManager.getSelectedFilter().collect { selectedFilter ->
          _selectedFilter.update { selectedFilter }
        }
      }
    }

  fun handleCreateNoteEvents(event: CreateNoteEvents) {
    when(event) {
      is CreateNoteEvents.TitleChanged -> {
        _createNoteState.update {
          it.copy(title = event.title)
        }
      }
      is CreateNoteEvents.DescriptionChanged -> {
        _createNoteState.update {
          it.copy(description = event.description)
        }
      }
      is CreateNoteEvents.PriorityChanged -> {
        _createNoteState.update {
          it.copy(priority = event.priority)
        }
      }
      is CreateNoteEvents.CreateNote -> {
        if(createNoteState.value.isValid()) {
          viewModelScope.launch {
            val noteEntity = NoteEntity(
              title = createNoteState.value.title ?: "",
              description = createNoteState.value.description ?: "",
              priority = createNoteState.value.priority ?: "",
              timestamp = System.currentTimeMillis(),
              noteLocation = createNoteState.value.noteLocation ?: ""
            )
            when(noteEntity.noteLocation) {
              "Internal Storage" -> {
                internalNotesFileManager.writeTextFile(noteEntity)
              }
              "External Storage" -> {
                externalNotesFileManager.writeTextFile(noteEntity)
              }
              else -> {
                // TODO: Implement other note locations
              }
            }
          }
        }
      }

      is CreateNoteEvents.NoteLocationChanged -> {
        _createNoteState.update {
          it.copy(noteLocation = event.noteLocation)
        }
      }
    }
  }

  private fun fetchNotes() {
    viewModelScope.launch {
      _notes.update {
         internalNotesFileManager.readTextFile() + externalNotesFileManager.readTextFile()
      }
    }
  }
}