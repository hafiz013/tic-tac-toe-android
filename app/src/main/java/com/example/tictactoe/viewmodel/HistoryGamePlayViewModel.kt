package com.example.tictactoe.viewmodel

import com.example.tictactoe.model.Player
import kotlinx.coroutines.flow.StateFlow

interface HistoryGamePlayViewModel {
    val uiState: StateFlow<HistoryGamePlayUiState>
    fun getHistoryGamePlay()
    sealed interface HistoryGamePlayUiState{
        object Loading: HistoryGamePlayUiState
        data class Success(val player:List<Player>): HistoryGamePlayUiState
        object Failed: HistoryGamePlayUiState
    }
}