package com.kodeco.android.devscribe.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.devscribe.data.datastore.DataStoreManager
import com.kodeco.android.devscribe.data.local.NoteEntity
import com.kodeco.android.devscribe.repository.NotesRepository
import com.kodeco.android.devscribe.ui.state.CreateNoteEvents
import com.kodeco.android.devscribe.ui.state.CreateNoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
  private val dataStoreManager: DataStoreManager,
  private val notesRepository: NotesRepository
): ViewModel() {
  private val _noteSource = MutableStateFlow(0)
  val noteSource = _noteSource.asStateFlow()

  private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
  val notes = _notes.asStateFlow()

  init {
    fetchNotes()
    fetchNoteSource()
  }


  private val _createNoteState = MutableStateFlow(CreateNoteState())
  val createNoteState = _createNoteState.asStateFlow()

     fun saveNoteSource(source: Int) {
       viewModelScope.launch {
           dataStoreManager.saveNoteSource(source)
       }
    }

    private fun fetchNoteSource() {
        dataStoreManager.getNoteSource().let {
            _noteSource.update {
                it
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
              description = _createNoteState.value.description ?: "",
              priority = _createNoteState.value.priority ?: "",
              timestamp = System.currentTimeMillis()
            )
            notesRepository.saveNote(noteEntity)
          }
        }
      }
    }
  }

  private fun fetchNotes() {
    viewModelScope.launch {
      notesRepository.getNotes().collect { notes ->
        _notes.update {
          notes
        }
      }
    }
  }
}