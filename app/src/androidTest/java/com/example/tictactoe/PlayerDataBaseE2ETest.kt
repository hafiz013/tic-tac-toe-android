package com.example.tictactoe

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tictactoe.database.UserDatabase
import com.example.tictactoe.model.Player
import com.example.tictactoe.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class PlayerDataBaseE2ETest {
    val testModule = module {
        fun provideDatabase(application: Application) = UserDatabase.getInstance(application)
        fun provideDao(database: UserDatabase) = database.getPlayerDao()
        single { provideDatabase(androidApplication()) }
        single { UserRepository(provideDao(get())) }
    }
    @Before
    fun setUp() {
        loadKoinModules(testModule)
    }
    @Test
    fun saveUserDetailsPlayerAndVerifyListOfPlayer() = runBlocking{
        val userRepository: UserRepository by inject(UserRepository::class.java)
        userRepository.insertAllUser(arrayListOf(Player(playerName = "test1", score = 4, percentageWin = 0.5),
            Player(playerName = "test2", score = 5, percentageWin = 0.7)))
        assertEquals(2, userRepository.getAllUser().size)
    }
}