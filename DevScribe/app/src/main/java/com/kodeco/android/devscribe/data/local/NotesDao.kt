package com.kodeco.android.devscribe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(note: NoteEntity)

  @Query("SELECT * FROM Notes")
  fun getNotes(): Flow<List<NoteEntity>>
}