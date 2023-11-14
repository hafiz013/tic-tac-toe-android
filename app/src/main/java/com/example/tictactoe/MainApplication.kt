package com.example.tictactoe

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.tictactoe.core.SharedPreferenceManager
import com.example.tictactoe.di.allModules
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    private val sharedPreferences: SharedPreferenceManager by inject()
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(androidContext = this@MainApplication)
            modules(allModules)
        }

        sharedPreferences.getThemeIsLight()?.let {
            val nightMode = if (it) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
    }
}