package com.example.tictactoe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.tictactoe.base.GameAbstractActivity
import com.example.tictactoe.viewmodel.GamePlayImpl
import org.koin.android.ext.android.inject

class GamePlay: GameAbstractActivity() {
    private val gamePlayImpl:GamePlayImpl by inject()
    companion object{
        private val PLAYER_1 = "PLAYER_1"
        private val PLAYER_2 = "PLAYER_2"
        val GAME_SIZE = "GAME_SIZE"
        fun newIntent(context: Context, playerName1: String, playerName2: String, gameSize:Int) {
            val intent = Intent(context, GamePlay::class.java)
            intent.putExtra(PLAYER_1, playerName1)
            intent.putExtra(PLAYER_2, playerName2)
            intent.putExtra(GAME_SIZE, gameSize)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPlayersNames()
    }
    private fun setPlayersNames() {
        player1Name?.text = intent.getStringExtra(PLAYER_1)
        player2Name?.text = intent.getStringExtra(PLAYER_2)
    }
    override fun setContinueButtonOnClickListener() {
        continueButton?.setOnClickListener { v ->
            resetBoard()
            disableControlButtons()
            updateSymbols()
        }
    }
}