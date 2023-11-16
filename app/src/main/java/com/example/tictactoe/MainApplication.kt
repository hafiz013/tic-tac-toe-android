package com.example.tictactoe

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.tictactoe.core.SharedPreferenceManager
import com.example.tictactoe.database.UserDatabase
import com.example.tictactoe.repository.UserRepository
import com.example.tictactoe.viewmodel.GamePlayImpl
import com.example.tictactoe.viewmodel.HistoryGamePlayImpl
import com.example.tictactoe.viewmodel.HistoryGamePlayViewModel
import com.example.tictactoe.viewmodel.SettingsViewModelImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MainApplication : Application() {
    private val sharedPreferences: SharedPreferenceManager by inject()
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(androidContext = this@MainApplication)
            modules(listOf(appModule))
        }

        sharedPreferences.getThemeIsLight()?.let {
            val nightMode = if (it) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
    }

    private val appModule = module{
        fun provideDatabase(application: Application) = UserDatabase.getInstance(application)
        fun provideDao(database: UserDatabase) = database.getPlayerDao()
        single { SharedPreferenceManager(androidApplication()) }
        single { provideDatabase(androidApplication()) }
        single { UserRepository(provideDao(get()))}
        viewModel { SettingsViewModelImpl(get()) }
        viewModel { GamePlayImpl(get()) }
        viewModel { HistoryGamePlayImpl(get())}
    }
}