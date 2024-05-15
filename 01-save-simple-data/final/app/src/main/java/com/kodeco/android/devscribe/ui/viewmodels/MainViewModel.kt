package com.kodeco.android.devscribe.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.devscribe.data.datastore.DataStoreManager
import com.kodeco.android.devscribe.data.local.NoteEntity
import com.kodeco.android.devscribe.ui.state.CreateNoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
  private val dataStoreManager: DataStoreManager
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
}