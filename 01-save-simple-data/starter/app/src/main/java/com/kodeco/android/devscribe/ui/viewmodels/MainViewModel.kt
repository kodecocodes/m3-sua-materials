package com.kodeco.android.devscribe.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.kodeco.android.devscribe.data.local.NoteEntity
import com.kodeco.android.devscribe.ui.state.CreateNoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
): ViewModel() {
  private val _selectedFilter = MutableStateFlow("")
  val selectedFilter = _selectedFilter.asStateFlow()

  private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
  val notes = _notes.asStateFlow()

  init {
    fetchSelectedFilter()
  }


  private val _createNoteState = MutableStateFlow(CreateNoteState())
  val createNoteState = _createNoteState.asStateFlow()

     fun saveSelectedFilter(selectedFilter: String) {
     // TODO: Save selected filter
    }

    private fun fetchSelectedFilter() {
    // TODO: Fetch selected filter and update UI state
    }
}