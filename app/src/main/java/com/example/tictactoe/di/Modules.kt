package com.example.tictactoe.di

import com.example.tictactoe.core.SharedPreferenceManager
import com.example.tictactoe.viewmodel.GamePlayImpl
import com.example.tictactoe.viewmodel.SettingsViewModelImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { SharedPreferenceManager(androidApplication()) }
}

val viewModels = module{
    viewModel { SettingsViewModelImpl(get()) }
    viewModel { GamePlayImpl()}
}

val allModules = listOf(appModule, viewModels)