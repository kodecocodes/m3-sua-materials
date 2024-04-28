package com.kodeco.android.devscribe.repository

import com.kodeco.android.devscribe.data.local.NoteEntity
import com.kodeco.android.devscribe.data.local.NotesDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface NotesRepository {
  suspend fun saveNote(noteEntity: NoteEntity)
  fun getNotes(): Flow<List<NoteEntity>>
}

class NotesRepositoryImpl(
  private val ioDispatcher: CoroutineDispatcher,
  private val notesDao: NotesDao
): NotesRepository {
  override suspend fun saveNote(noteEntity: NoteEntity) {
    withContext(ioDispatcher) {
      notesDao.insert(noteEntity)
    }
  }

  override fun getNotes(): Flow<List<NoteEntity>> {
    return notesDao.getNotes()
  }
}