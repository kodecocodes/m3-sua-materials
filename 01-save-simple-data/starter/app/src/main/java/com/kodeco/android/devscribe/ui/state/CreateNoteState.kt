package com.kodeco.android.devscribe.ui.state

data class CreateNoteState(
  val title: String? = null,
  val description: String? = null,
  val priority: String? = null,
  val noteLocation: String? = null
) {
   fun isValid(): Boolean {
     return title != null && description != null && priority != null && noteLocation != null
   }
}

sealed interface CreateNoteEvents {
  data class TitleChanged(val title: String) : CreateNoteEvents
  data class DescriptionChanged(val description: String) : CreateNoteEvents
  data class PriorityChanged(val priority: String) : CreateNoteEvents

  data class NoteLocationChanged(val noteLocation: String) : CreateNoteEvents
  data object CreateNote : CreateNoteEvents
}
