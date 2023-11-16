package com.example.tictactoe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tictactoe.data.PlayerDao
import com.example.tictactoe.model.Player

@Database(entities = [Player::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){
    abstract fun getPlayerDao(): PlayerDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Synchronized
        fun getInstance(context: Context): UserDatabase {
            return Room.databaseBuilder(context.applicationContext,
                UserDatabase::class.java,
                "user.db").build()
        }
    }
}