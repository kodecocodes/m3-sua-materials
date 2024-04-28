package com.kodeco.android.devscribe.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

  private val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = "devscribe")

    suspend fun saveNoteSource(source: Int) {
        val dataStoreKey = intPreferencesKey("note_source")
        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = source
        }
    }

   fun getNoteSource(): Flow<Int> {
        return context.dataStore.data
            .map { preferences ->
                preferences[intPreferencesKey("note_source")] ?: 0
            }
    }
}