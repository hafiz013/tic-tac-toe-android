package com.example.tictactoe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryGamePlayImpl(private val userRepository: UserRepository):ViewModel(), HistoryGamePlayViewModel {
    private val  _state = MutableStateFlow<HistoryGamePlayViewModel.HistoryGamePlayUiState>(
        HistoryGamePlayViewModel.HistoryGamePlayUiState.Loading
    )
    override val uiState: StateFlow<HistoryGamePlayViewModel.HistoryGamePlayUiState>
        get() = _state.asStateFlow()

    override fun getHistoryGamePlay() {
        _state.value = HistoryGamePlayViewModel.HistoryGamePlayUiState.Loading

        viewModelScope.launch {
            if (userRepository.getAllUser().isEmpty()){
                _state.value = HistoryGamePlayViewModel.HistoryGamePlayUiState.Failed
            }else{
                _state.value = HistoryGamePlayViewModel.HistoryGamePlayUiState.Success(userRepository.getAllUser())
            }
        }
    }
}