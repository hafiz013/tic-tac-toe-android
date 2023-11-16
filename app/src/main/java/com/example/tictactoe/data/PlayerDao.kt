package com.example.tictactoe.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tictactoe.model.Player

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlayer(players: List<Player>):List<Long>

    @Query("SELECT * FROM players")
    suspend fun getAllPlayers(): List<Player>

    @Query("DELETE FROM players")
    suspend fun deleteAll()
}