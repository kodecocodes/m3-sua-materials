package com.kodeco.android.devscribe.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class NoteEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val title: String,
  val description: String,
  val timestamp: Long,
  val priority: String,
  val noteLocation: String
)
