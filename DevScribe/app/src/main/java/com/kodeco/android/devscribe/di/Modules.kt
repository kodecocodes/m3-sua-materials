package com.kodeco.android.devscribe.di

import androidx.room.Room
import com.kodeco.android.devscribe.data.datastore.DataStoreManager
import com.kodeco.android.devscribe.data.local.DevScribeDatabase
import com.kodeco.android.devscribe.repository.NotesRepository
import com.kodeco.android.devscribe.repository.NotesRepositoryImpl
import com.kodeco.android.devscribe.ui.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataStoreModule = module {
    single { DataStoreManager(androidContext()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}


val dispatcherModule = module {
    single { Dispatchers.IO }
}

val repositoryModule = module {
    single<NotesRepository> { NotesRepositoryImpl(get(), get()) }
}

val roomDatabaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), DevScribeDatabase::class.java, "dev_scribe_db")
            .build()
    }

    single { get<DevScribeDatabase>().notesDao() }
}


val appModules = listOf(
    dataStoreModule,
    viewModelModule,
    dispatcherModule,
    repositoryModule,
    roomDatabaseModule
)