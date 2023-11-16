package com.example.tictactoe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.model.Player
import com.example.tictactoe.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GamePlayImpl(private val userRepository: UserRepository):ViewModel(), GamePlayViewModel {
    // - Repository is completely separated from the UI through the ViewModel.
    private val  _state = MutableStateFlow<GamePlayViewModel.GamePlayUiState>(GamePlayViewModel.GamePlayUiState.Loading)
    val uiState:StateFlow<GamePlayViewModel.GamePlayUiState> get() = _state.asStateFlow()
    override fun saveDetailsPlayer(
        player1Name: String,
        scorePlayer1: Int,
        winRatePlayer1: String,
        player2Name: String,
        scorePlayer2: Int,
        winRatePlayer2: String
    ) {
        val playerList = ArrayList<Player>()
        playerList.add(Player(playerName = player1Name, score = scorePlayer1, percentageWin = winRatePlayer1.replace("%", "").toDouble()))
        playerList.add(Player(playerName = player2Name, score = scorePlayer2, percentageWin = winRatePlayer2.replace("%", "").toDouble()))

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val insertRowId = userRepository.insertAllUser(playerList = playerList)

                if (insertRowId.size > 0){
                    _state.value = GamePlayViewModel.GamePlayUiState.Succes
                }else{
                    _state.value = GamePlayViewModel.GamePlayUiState.Failed
                }
            }
        }
    }
}