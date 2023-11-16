package com.example.tictactoe.repository

import androidx.annotation.WorkerThread
import com.example.tictactoe.data.PlayerDao
import com.example.tictactoe.model.Player
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: PlayerDao) {
    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    suspend fun insertAllUser(playerList: ArrayList<Player>):List<Long> {
       return userDao.insertAllPlayer(playerList)
    }
    @WorkerThread
    suspend fun getAllUser(): List<Player> {
        return userDao.getAllPlayers()
    }
}