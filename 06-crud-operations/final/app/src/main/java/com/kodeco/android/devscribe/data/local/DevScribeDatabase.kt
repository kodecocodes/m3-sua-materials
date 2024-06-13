package com.kodeco.android.devscribe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class DevScribeDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao
}