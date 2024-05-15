package com.kodeco.android.devscribe.di

import com.kodeco.android.devscribe.ui.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataStoreModule = module {

}

val viewModelModule = module {
    viewModel { MainViewModel() }
}

val appModules = listOf(
    dataStoreModule,
    viewModelModule
)