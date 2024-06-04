package com.kodeco.android.devscribe.di

import com.kodeco.android.devscribe.data.datastore.DataStoreManager
import com.kodeco.android.devscribe.data.files.ExternalNotesFileManager
import com.kodeco.android.devscribe.data.files.InternalNotesFileManager
import com.kodeco.android.devscribe.ui.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataStoreModule = module {
    single { DataStoreManager(androidContext()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get(), get(), get()) }
}

val notesFileManagerModule = module {
    single { InternalNotesFileManager(androidContext()) }
    single { ExternalNotesFileManager(androidContext()) }
}

val appModules = listOf(
    dataStoreModule,
    viewModelModule,
    notesFileManagerModule
)