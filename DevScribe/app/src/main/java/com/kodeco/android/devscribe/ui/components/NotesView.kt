package com.kodeco.android.devscribe.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.devscribe.data.local.NoteEntity
import com.kodeco.android.devscribe.ui.theme.DevScribeTheme

@Composable
fun NotesView(notes: List<NoteEntity>) {
  LazyColumn(
      modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
  ) {
    items(notes) { note ->
      NoteListItem(
          note = note
      )
    }
  }
}

@Composable
fun NoteListItem(note: NoteEntity) {
  Text(
      modifier = Modifier
          .fillMaxWidth()
          .padding(top = 6.dp),
      text = note.title,
  )
}



@Preview(showBackground = true)
@Composable
fun NotesViewPreview() {
 DevScribeTheme {
    NotesView(notes = emptyList())
 }
}