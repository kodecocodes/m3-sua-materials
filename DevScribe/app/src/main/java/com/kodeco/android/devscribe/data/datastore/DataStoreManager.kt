package com.kodeco.android.devscribe.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

  private val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = "devscribe")

    suspend fun saveSelectedFilter(selectedFilter: String) {
        val dataStoreKey = stringPreferencesKey("filters")
        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = selectedFilter
        }
    }

   fun getSelectedFilter(): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[stringPreferencesKey("filters")] ?: "All"
            }
    }
}