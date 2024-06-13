package com.kodeco.android.devscribe.data.files

import android.content.Context
import android.os.Environment
import com.kodeco.android.devscribe.data.local.NoteEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class ExternalNotesFileManager(
  private val context: Context
) {
  fun writeTextFile(note: NoteEntity) {
    if (!isExternalStorageWritable()) {
      return
    }
    val directory = File(context.getExternalFilesDir(null), DIRECTORY_NAME)
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

  fun readTextFile(): List<NoteEntity> {
    if (!isExternalStorageReadable()) {
      return emptyList()
    }
    val directory = File(context.getExternalFilesDir(null), DIRECTORY_NAME)
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

  private  fun isExternalStorageWritable(): Boolean =
    Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

  private fun isExternalStorageReadable(): Boolean =
    Environment.getExternalStorageState() in
        setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)

  companion object {
    const val DIRECTORY_NAME = "DevScribeNotesExternal"
  }
}