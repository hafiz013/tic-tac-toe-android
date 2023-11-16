package com.example.tictactoe.viewmodel

interface GamePlayViewModel {
    fun saveDetailsPlayer(
        player1Name: String,
        scorePlayer1: Int,
        winRatePlayer1: String,
        player2Name: String,
        scorePlayer2: Int,
        winRatePlayer2: String
    )
    sealed interface GamePlayUiState{
        object Loading: GamePlayUiState
        object Succes: GamePlayUiState
        object Failed: GamePlayUiState
    }
}