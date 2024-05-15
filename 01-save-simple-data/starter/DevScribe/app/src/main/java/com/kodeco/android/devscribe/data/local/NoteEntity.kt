package com.kodeco.android.devscribe.data.local

import kotlinx.serialization.Serializable

@Serializable
data class NoteEntity(
  val id: Int = 0,
  val title: String,
  val description: String,
  val timestamp: Long,
  val priority: String,
  val noteLocation: String
)
