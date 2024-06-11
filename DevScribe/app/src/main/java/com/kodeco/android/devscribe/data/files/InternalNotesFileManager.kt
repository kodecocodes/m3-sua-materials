package com.kodeco.android.devscribe.data.files

import android.content.Context
import com.kodeco.android.devscribe.data.local.NoteEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class InternalNotesFileManager(
  private val context: Context
) {
   fun readTextFile(): List<NoteEntity> {
    val directory = File(context.filesDir, DIRECTORY_NAME)
    val noteFiles = directory.listFiles()?.filter { it.isFile && it.name.endsWith(".txt") }
    val notes = mutableListOf<NoteEntity>()
    noteFiles?.forEach { file ->
      try {
        val inputStream = FileInputStream(file)
        val content = inputStream.bufferedReader().use { it.readText() }
        notes.add(
          Json.decodeFromString(content)
        )
      } catch (e: IOException) {
        e.printStackTrace()
      }
    }
    return notes
  }

   fun writeTextFile(note: NoteEntity) {
    val directory = File(context.filesDir, DIRECTORY_NAME)
    if (!directory.exists()) {
      directory.mkdirs()
    }
    val file = File(directory, "${note.title}.txt")
    try {
      file.outputStream().use { outputStream ->
        outputStream.write(Json.encodeToString(note).toByteArray())
      }
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  companion object {
    const val DIRECTORY_NAME = "DevScribeNotesInternal"
  }
}