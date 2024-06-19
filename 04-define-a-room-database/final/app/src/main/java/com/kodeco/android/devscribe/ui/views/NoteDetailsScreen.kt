package com.kodeco.android.devscribe.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodeco.android.devscribe.R
import com.kodeco.android.devscribe.data.local.NoteEntity
import com.kodeco.android.devscribe.ui.viewmodels.MainViewModel
import com.kodeco.android.devscribe.utils.formatTime
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
  navigateBack: () -> Unit,
  note: NoteEntity?,
  editNote: (NoteEntity) -> Unit
) {
  val viewModel: MainViewModel = koinInject()
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = stringResource(id = R.string.title_note_details)
          )
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primary,
          titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
          IconButton(
            onClick = navigateBack,
            content = {
              Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
              )
            }
          )
        },
        actions = {
          val showActions = note != null && note.noteLocation == "Room Database"
          AnimatedVisibility(
            visible = showActions
          ) {
            IconButton(
              onClick = {
                note?.let {
                  editNote(it)
                }
              },
              content = {
                Icon(
                  imageVector = Icons.Default.Edit,
                  contentDescription = "Edit",
                  tint = Color.White
                )
              }
            )
          }

          AnimatedVisibility(
            visible = showActions
          ) {
            IconButton(
              onClick = {
                note?.let {
                  // TODO: Implement delete functionality
                }
              },
              content = {
                Icon(
                  imageVector = Icons.Default.Delete,
                  contentDescription = "Delete",
                  tint = Color.White
                )
              }
            )
          }
        }
      )
    },
    content = {
      Column(
        modifier = Modifier
          .padding(it)
      ) {
        note?.let { note ->
          NoteDetailsScreenContent(
            note = note
          )
        }
      }
    }
  )
}

@Composable
fun NoteDetailsScreenContent(note: NoteEntity) {
  Column(
    modifier = Modifier
      .padding(10.dp)
  ) {

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = note.title,
          fontWeight = FontWeight.Bold
        )

        Box(
          modifier = Modifier
            .padding(top = 6.dp)
            .clip(shape = RoundedCornerShape(24.dp))
            .background(
              when (note.priority) {
                "High" -> Color.Red
                "Medium" -> Color.Yellow
                else -> Color.Blue
              }
            )
            .padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 3.dp)
        ) {
          Text(
            text = note.priority,
            color = if (note.priority == "High" || note.priority == "Low") Color.White else Color.Black
          )
        }
      }
      val icon = when(note.noteLocation) {
        "Internal Storage" -> R.drawable.baseline_smartphone_24
        "External Storage" -> R.drawable.baseline_sd_storage_24
        else -> R.drawable.baseline_storage_24
      }
      Icon(
        painter = painterResource(id = icon),
        contentDescription = "Storage location icon",
        tint = MaterialTheme.colorScheme.primary
      )
    }

    HorizontalDivider(
      modifier = Modifier
        .padding(10.dp),
      color = Color.LightGray.copy(alpha = 0.5f)
    )

    Text(
      modifier = Modifier
        .padding(top = 16.dp),
      text = "Description",
      fontWeight = FontWeight.Bold
    )

    Text(
      modifier = Modifier
        .padding(top = 6.dp),
      text = note.description,
      color = Color.Gray
    )

    Text(
      modifier = Modifier
        .padding(top = 6.dp),
      text = "Created on: ${formatTime(note.timestamp)}",
      fontSize = 12.sp,
      color = Color.Gray
    )
  }
}