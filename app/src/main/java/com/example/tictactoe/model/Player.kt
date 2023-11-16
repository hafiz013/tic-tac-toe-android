package com.example.tictactoe.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Player(@PrimaryKey @ColumnInfo(name = "id")
                  val id:Long = (1..1200).random().toLong(),
                  @ColumnInfo(name = "player_name")
                  val playerName:String,
                  @ColumnInfo(name = "score")
                  val score: Int,
                  @ColumnInfo(name = "percentage_win")
                  val percentageWin:Double)