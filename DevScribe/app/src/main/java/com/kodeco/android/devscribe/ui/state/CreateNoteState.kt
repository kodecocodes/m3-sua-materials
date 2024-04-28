package com.kodeco.android.devscribe.ui.state

data class CreateNoteState(
  val title: String? = null,
  val description: String? = null,
  val priority: String? = null
) {
   fun isValid(): Boolean {
     return title != null && description != null && priority != null
   }
}

sealed class CreateNoteEvents {
  data class TitleChanged(val title: String) : CreateNoteEvents()
  data class DescriptionChanged(val description: String) : CreateNoteEvents()
  data class PriorityChanged(val priority: String) : CreateNoteEvents()
  data object CreateNote : CreateNoteEvents()
}
