package com.kodeco.android.devscribe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.devscribe.data.local.NoteEntity
import com.kodeco.android.devscribe.ui.theme.DevScribeTheme
import com.kodeco.android.devscribe.utils.formatTime

@Composable
fun NotesView(notes: List<NoteEntity>) {
  LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
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
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 8.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    shape = RoundedCornerShape(10.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = note.title,
        )
        Box(
          modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(
              when (note.priority) {
                "High" -> Color.Red
                "Medium" -> Color.Yellow
                else -> Color.Blue
              }
            )
            .padding(start = 10.dp, end = 10.dp, top = 3.dp, bottom = 3.dp)
        ) {
          Text(
            text = note.priority,
            color = if (note.priority == "High" || note.priority == "Low") Color.White else Color.Black
          )
        }
      }
      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 6.dp),
        text = formatTime(note.timestamp),
      )
    }
  }
}



@Preview(showBackground = true)
@Composable
fun NotesViewPreview() {
 DevScribeTheme {
    NotesView(notes = emptyList())
 }
}