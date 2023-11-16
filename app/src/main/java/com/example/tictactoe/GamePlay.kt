package com.example.tictactoe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.lifecycleScope
import com.example.tictactoe.base.GameAbstractActivity
import com.example.tictactoe.model.Player
import com.example.tictactoe.viewmodel.GamePlayImpl
import com.example.tictactoe.viewmodel.GamePlayViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class GamePlay: GameAbstractActivity() {
    private val gamePlayImpl:GamePlayImpl by inject()
    private var progressDialog: AppCompatDialog? = null
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
        exitButton?.setOnClickListener {
            if (totalGames == 0){
                closeCurrentPage()
                return@setOnClickListener
            }

            gamePlayImpl.saveDetailsPlayer(player1Name?.text.toString(), scorePlayer1Text?.text.toString().toInt(),
                winRatePlayer1?.text.toString(), player2Name?.text.toString(), scorePlayer2Text?.text.toString().toInt(),
                winRatePlayer2?.text.toString())

            lifecycleScope.launch {
                gamePlayImpl.uiState.collect{ uiState ->
                    withContext(Dispatchers.Main) {
                        when(uiState){
                            GamePlayViewModel.GamePlayUiState.Succes ->{
                                hideProgressDialog()
                                Toast.makeText(this@GamePlay, resources.getString(R.string.success_save_data), Toast.LENGTH_SHORT).show()
                                closeCurrentPage()
                            }
                            GamePlayViewModel.GamePlayUiState.Failed ->{
                                hideProgressDialog()
                                Toast.makeText(this@GamePlay, resources.getString(R.string.failed_save_data), Toast.LENGTH_SHORT).show()
                                closeCurrentPage()
                            }
                            else ->{
                                showProgressDialog()
                            }
                        }
                    }
                }
            }
        }
        resetButton?.setOnClickListener {
            resetBoard()
            disableControlButtons()
            updateSymbols()
            resetResultGame()
        }
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
    private fun showProgressDialog() {
        progressDialog = AppCompatDialog(this)
        progressDialog?.setTitle(resources.getString(R.string.loading)) // Set your loading message
        progressDialog?.setCancelable(true)
        progressDialog?.show()
    }

    private fun hideProgressDialog() {
        progressDialog?.dismiss()
    }
    private fun closeCurrentPage(){
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}