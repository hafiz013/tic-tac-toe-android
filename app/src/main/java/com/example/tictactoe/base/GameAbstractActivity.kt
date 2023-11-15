package com.example.tictactoe.base

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.GamePlay.Companion.GAME_SIZE
import com.example.tictactoe.R
import com.example.tictactoe.core.SharedPreferenceManager
import com.example.tictactoe.databinding.ActivityGame3x3Binding
import com.example.tictactoe.databinding.ActivityGame4x4Binding
import com.example.tictactoe.databinding.ActivityGame5x5Binding
import com.example.tictactoe.extenstion.showAnimatedToast
import com.example.tictactoe.setting.TicTacToeLogic
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import org.koin.android.ext.android.inject
import java.lang.String
import java.text.DecimalFormat
import kotlin.Array
import kotlin.Boolean
import kotlin.Char
import kotlin.Int
import kotlin.IntArray
import kotlin.arrayOf
import kotlin.arrayOfNulls
import kotlin.getValue
import kotlin.let
import kotlin.toString

abstract class GameAbstractActivity : AppCompatActivity() {
    protected var activityGame3x3Binding: ActivityGame3x3Binding? = null
    protected var activityGame4x4Binding: ActivityGame4x4Binding? = null
    protected var activityGame5x5Binding: ActivityGame5x5Binding? = null
    protected var buttons:Array<Array<ImageButton?>> = arrayOf()
    protected var texts:Array<Array<TextView?>> = arrayOf()
    private lateinit var gameLogic: TicTacToeLogic
    protected var currentPlayerSymbolText: MaterialTextView? = null
    protected var player1Name: MaterialTextView? = null
    protected var player2Name: MaterialTextView? = null
    protected var scorePlayer1Text: MaterialTextView? = null
    protected var scorePlayer2Text: MaterialTextView? = null
    protected var currentPlayerChar = 'X'
    protected var Player1SymbolIsX = true
    protected var exitButton: MaterialButton? = null
    protected var continueButton: MaterialButton? = null
    protected var resetButton:MaterialButton? = null
    protected var winRatePlayer1:MaterialTextView? = null
    protected var winRatePlayer2:MaterialTextView? = null
    protected var player1CurrentSymbolText: MaterialTextView? = null
    protected var player2CurrentSymbolText: MaterialTextView? = null
    protected var gameSize:Int = 0
    protected var totalGames = 0
    protected var totalGamesWonByPlayer1 = 0
    protected var totalGamesWonByPlayer2 = 0
    private var mediaPlayer: MediaPlayer? = null
    private val sharedPreferences: SharedPreferenceManager by inject()
    protected abstract fun setContinueButtonOnClickListener()
    private fun setButtonsOnClickListeners(){
        for (i in 0 until buttons.size) {
            for (j in 0 until buttons[i].size) {
                buttons[i][j]?.setOnClickListener { v ->
                    texts[i][j]?.text = String.valueOf(currentPlayerChar)
                    buttons[i][j]?.isClickable = false
                    gameLogic.makeMove(i, j, currentPlayerChar)
                    if (!checkGameFinish()) {
                        if (currentPlayerChar === 'X') {
                            currentPlayerSymbolText?.text = "O"
                            currentPlayerChar = 'O'
                        } else {
                            currentPlayerSymbolText?.text = "X"
                            currentPlayerChar = 'X'
                        }
                    }
                }
            }
        }
    }
    private fun bindCurrentPlayerSymbolTextToVariable(){
        currentPlayerSymbolText = if(activityGame3x3Binding != null){
            activityGame3x3Binding?.currentSymbol
        }else if(activityGame4x4Binding != null){
            activityGame4x4Binding?.currentSymbol
        }else{
            activityGame5x5Binding?.currentSymbol
        }
    }

    private fun bindWinRatePlayer(){
        winRatePlayer1 = if(activityGame3x3Binding != null){
            activityGame3x3Binding?.winRate1
        }else if(activityGame4x4Binding != null){
            activityGame4x4Binding?.winRate1
        }else{
            activityGame5x5Binding?.winRate1
        }

        winRatePlayer2 = if(activityGame3x3Binding != null){
            activityGame3x3Binding?.winRate2
        }else if(activityGame4x4Binding != null){
            activityGame4x4Binding?.winRate2
        }else{
            activityGame5x5Binding?.winRate2
        }
    }
    protected fun updateSymbols() {
        Player1SymbolIsX = !Player1SymbolIsX
        if (Player1SymbolIsX) {
            player1CurrentSymbolText?.text = "X"
            player2CurrentSymbolText?.text = "O"
        } else {
            player1CurrentSymbolText?.text = "O"
            player2CurrentSymbolText?.text = "X"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameSize = intent.getIntExtra(GAME_SIZE, 0)
        gameLogic  = TicTacToeLogic(gameSize)
        buttons = Array(this.gameSize) { arrayOfNulls<ImageButton>(gameSize) }
        texts  = Array(this.gameSize) { arrayOfNulls<TextView>(gameSize) }

        when(gameSize){
            3 ->{
                activityGame3x3Binding = ActivityGame3x3Binding.inflate(layoutInflater)
                setContentView(activityGame3x3Binding?.root)
                bindButtons3x3Layer()
                bindTexts3x3Layer()
            }
            4 -> {
                activityGame4x4Binding = ActivityGame4x4Binding.inflate(layoutInflater)
                setContentView(activityGame4x4Binding?.root)
                bindButtons4x4Layer()
                bindTexts4x4Layer()
            }
            else ->{
                activityGame5x5Binding = ActivityGame5x5Binding.inflate(layoutInflater)
                setContentView(activityGame5x5Binding?.root)
                bindButtons5x5Layer()
                bindTexts5x5Layer()
            }
        }

        createMediaPlayer()
        bindCurrentPlayerSymbolTextToVariable()
        bindWinRatePlayer()
        bindPlayerScoreTextsToVariables()
        bindPlayerNameToVariables()
        bindControlButtonsToVariables()
        setControlButtonsOnClickListeners()
        bindPlayerCurrentSymbolsTextsToVariables()
        setButtonsOnClickListeners()
    }

    private fun createMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.congrate)
    }

    private fun updateResult(result: Char) {
        if (Player1SymbolIsX) {
            if (result == 'X') {
                scorePlayer1Text?.text =
                    (scorePlayer1Text?.getText().toString().toInt() + 1).toString()
                totalGamesWonByPlayer1 = scorePlayer1Text?.getText().toString().toInt()
            } else if (result == 'O') {
                scorePlayer2Text?.text =
                    (scorePlayer2Text?.getText().toString().toInt() + 1).toString()
                totalGamesWonByPlayer2 = scorePlayer2Text?.getText().toString().toInt()
            }
        } else {
            if (result == 'O') {
                scorePlayer1Text?.text =
                    (scorePlayer1Text?.getText().toString().toInt() + 1).toString()
                totalGamesWonByPlayer1 = scorePlayer1Text?.getText().toString().toInt()
            } else if (result == 'X') {
                scorePlayer2Text?.text =
                    (scorePlayer2Text?.getText().toString().toInt() + 1).toString()
                totalGamesWonByPlayer2 = scorePlayer2Text?.getText().toString().toInt()
            }
        }
    }

    private fun enableContinueButton() {
        continueButton?.setEnabled(true)
    }

    protected fun checkGameFinish(): Boolean {
        val result: Char = gameLogic.checkResult()
        if (result != ' ') {
            totalGames++
            updateResult(result)
            showResultToast(result)
            disableButtons()
            updateWinRate()
            enableContinueButton()
            if (result != 'D') gameLogic.getWinPositions()?.let { setWinningButtonsBackground(it) }
            return true
        }
        return false
    }

    private fun updateWinRate() {
        if (totalGames == 0) {
            winRatePlayer1?.text = "0.0%"
            winRatePlayer2?.text = "0.0%"
        } else {
            winRatePlayer1?.text = "${DecimalFormat("0.00%").format(totalGamesWonByPlayer1.toDouble().div(totalGames.toDouble()))}%"
            winRatePlayer2?.text = "${DecimalFormat("0.00%").format(totalGamesWonByPlayer2.toDouble().div(totalGames.toDouble()))}%"
        }
    }

    private fun setWinningButtonsBackground(winningButtons: Array<IntArray>) {
        for (winningButton in winningButtons) {
            buttons[winningButton[0]][winningButton[1]]?.setColorFilter(Color.BLACK)
        }
    }

    private fun disableButtons() {
        for (buttonsRow in buttons) for (button in buttonsRow) button?.isClickable =
            false
    }

    private fun showResultToast(result: Char) {
        var winner: Int // 1 is player 1, -1 is player 2, 0 is draw
        winner = if (result == 'X') {
            -1
        } else if (result == 'O') {
            1
        } else 0
        if (Player1SymbolIsX) winner = -winner
        if (winner == 1){
            startPlayMusic()
            if(sharedPreferences.getAnimation()){
                showAnimatedToast(player1Name?.getText().toString() + " " + getString(R.string.won))
            }else{
                Toast.makeText(
                    this,
                    player1Name?.getText().toString() + " " + getString(R.string.won),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else if (winner == -1) {
            startPlayMusic()
            if(sharedPreferences.getAnimation()){
                showAnimatedToast(player2Name?.getText().toString() + " " + getString(R.string.won))
            }else{
                Toast.makeText(
                    this,
                    player2Name?.getText().toString() + " " + getString(R.string.won),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else Toast.makeText(this, getString(R.string.draw), Toast.LENGTH_SHORT).show()
    }

    private fun startPlayMusic(){
        if (sharedPreferences.getSound()){
            mediaPlayer?.setVolume(1.0f, 1.0f)
            mediaPlayer?.start()
        }
    }

    protected fun resetBoard() {
        for (i in buttons.indices) for (j in buttons[i].indices) {
            buttons[i][j]?.isClickable = true
            buttons[i][j]?.clearColorFilter()
            texts[i][j]?.text = ""
        }
        gameLogic.resetGame()
    }

    private fun setControlButtonsOnClickListeners() {
        setContinueButtonOnClickListener()
        setExitButtonOnClickListener()
    }

    protected fun persistMove(x: Int, y: Int, symbol: Char) {
        texts[x][y]?.text = symbol.toString()
        buttons[x][y]?.isClickable = false
    }

    private fun setExitButtonOnClickListener() {
        exitButton?.setOnClickListener { v: View? -> finish() }
    }

    protected fun disableControlButtons() {
        continueButton?.setEnabled(false)
    }

    private fun bindPlayerCurrentSymbolsTextsToVariables() {
        player1CurrentSymbolText = if(activityGame3x3Binding != null){
            activityGame3x3Binding?.simbolPlayer1
        }else if(activityGame4x4Binding != null){
            activityGame4x4Binding?.simbolPlayer1
        }else{
            activityGame5x5Binding?.simbolPlayer1
        }
        player2CurrentSymbolText = if(activityGame3x3Binding != null){
            activityGame3x3Binding?.simbolPlayer2
        }else if(activityGame4x4Binding != null){
            activityGame4x4Binding?.simbolPlayer2
        }else{
            activityGame5x5Binding?.simbolPlayer2
        }
    }

    private fun bindControlButtonsToVariables() {
        exitButton = if(activityGame3x3Binding != null){
            activityGame3x3Binding?.exitBtn
        }else if(activityGame4x4Binding != null){
            activityGame4x4Binding?.exitBtn
        }else{
            activityGame5x5Binding?.exitBtn
        }
        continueButton = if(activityGame3x3Binding != null){
            activityGame3x3Binding?.continueBtn
        }else if(activityGame4x4Binding != null){
            activityGame4x4Binding?.continueBtn
        }else{
            activityGame5x5Binding?.continueBtn
        }
        resetButton = if(activityGame3x3Binding != null){
            activityGame3x3Binding?.resetBtn
        }else if(activityGame4x4Binding != null){
            activityGame4x4Binding?.resetBtn
        }else{
            activityGame5x5Binding?.resetBtn
        }
    }

    private fun bindPlayerNameToVariables() {
        player1Name = if(activityGame3x3Binding != null){
            activityGame3x3Binding?.player1Txt
        }else if(activityGame4x4Binding != null){
            activityGame4x4Binding?.player1Txt
        }else{
            activityGame5x5Binding?.player1Txt
        }
        player2Name= if(activityGame3x3Binding != null){
            activityGame3x3Binding?.player2Txt
        }else if(activityGame4x4Binding != null){
            activityGame4x4Binding?.player2Txt
        }else{
            activityGame5x5Binding?.player2Txt
        }
    }

    private fun bindPlayerScoreTextsToVariables() {
        scorePlayer1Text= if(activityGame3x3Binding != null){
            activityGame3x3Binding?.mark1
        }else if(activityGame4x4Binding != null){
            activityGame4x4Binding?.mark1
        }else{
            activityGame5x5Binding?.mark1
        }
        scorePlayer2Text = if(activityGame3x3Binding != null){
            activityGame3x3Binding?.mark2
        }else if(activityGame4x4Binding != null){
            activityGame4x4Binding?.mark2
        }else{
            activityGame5x5Binding?.mark2
        }
    }

    private fun bindButtons3x3Layer(){
        buttons[0][0] = activityGame3x3Binding?.boardButton00
        buttons[0][1] = activityGame3x3Binding?.boardButton01
        buttons[0][2] = activityGame3x3Binding?.boardButton02
        buttons[1][0] = activityGame3x3Binding?.boardButton10
        buttons[1][1] = activityGame3x3Binding?.boardButton11
        buttons[1][2] = activityGame3x3Binding?.boardButton12
        buttons[2][0] = activityGame3x3Binding?.boardButton20
        buttons[2][1] = activityGame3x3Binding?.boardButton21
        buttons[2][2] = activityGame3x3Binding?.boardButton22
    }

    private fun bindButtons4x4Layer(){
        buttons[0][0] = activityGame4x4Binding?.boardButton00
        buttons[0][1] = activityGame4x4Binding?.boardButton01
        buttons[0][2] = activityGame4x4Binding?.boardButton02
        buttons[0][3] = activityGame4x4Binding?.boardButton03
        buttons[1][0] = activityGame4x4Binding?.boardButton10
        buttons[1][1] = activityGame4x4Binding?.boardButton11
        buttons[1][2] = activityGame4x4Binding?.boardButton12
        buttons[1][3] = activityGame4x4Binding?.boardButton13
        buttons[2][0] = activityGame4x4Binding?.boardButton20
        buttons[2][1] = activityGame4x4Binding?.boardButton21
        buttons[2][2] = activityGame4x4Binding?.boardButton22
        buttons[2][3] = activityGame4x4Binding?.boardButton23
        buttons[3][0] = activityGame4x4Binding?.boardButton30
        buttons[3][1] = activityGame4x4Binding?.boardButton31
        buttons[3][2] = activityGame4x4Binding?.boardButton32
        buttons[3][3] = activityGame4x4Binding?.boardButton33
    }

    private fun bindButtons5x5Layer(){
        buttons[0][0] = activityGame5x5Binding?.boardButton00
        buttons[0][1] = activityGame5x5Binding?.boardButton01
        buttons[0][2] = activityGame5x5Binding?.boardButton02
        buttons[0][3] = activityGame5x5Binding?.boardButton03
        buttons[0][4] = activityGame5x5Binding?.boardButton04
        buttons[1][0] = activityGame5x5Binding?.boardButton10
        buttons[1][1] = activityGame5x5Binding?.boardButton11
        buttons[1][2] = activityGame5x5Binding?.boardButton12
        buttons[1][3] = activityGame5x5Binding?.boardButton13
        buttons[1][4] = activityGame5x5Binding?.boardButton14
        buttons[2][0] = activityGame5x5Binding?.boardButton20
        buttons[2][1] = activityGame5x5Binding?.boardButton21
        buttons[2][2] = activityGame5x5Binding?.boardButton22
        buttons[2][3] = activityGame5x5Binding?.boardButton23
        buttons[2][4] = activityGame5x5Binding?.boardButton24
        buttons[3][0] = activityGame5x5Binding?.boardButton30
        buttons[3][1] = activityGame5x5Binding?.boardButton31
        buttons[3][2] = activityGame5x5Binding?.boardButton32
        buttons[3][3] = activityGame5x5Binding?.boardButton33
        buttons[3][4] = activityGame5x5Binding?.boardButton34
        buttons[4][0] = activityGame5x5Binding?.boardButton40
        buttons[4][1] = activityGame5x5Binding?.boardButton41
        buttons[4][2] = activityGame5x5Binding?.boardButton42
        buttons[4][3] = activityGame5x5Binding?.boardButton43
        buttons[4][4] = activityGame5x5Binding?.boardButton44
    }

    private fun bindTexts3x3Layer(){
        texts[0][0] = activityGame3x3Binding?.boardText00
        texts[0][1] = activityGame3x3Binding?.boardText01
        texts[0][2] = activityGame3x3Binding?.boardText02
        texts[1][0] = activityGame3x3Binding?.boardText10
        texts[1][1] = activityGame3x3Binding?.boardText11
        texts[1][2] = activityGame3x3Binding?.boardText12
        texts[2][0] = activityGame3x3Binding?.boardText20
        texts[2][1] = activityGame3x3Binding?.boardText21
        texts[2][2] = activityGame3x3Binding?.boardText22
    }

    private fun bindTexts4x4Layer(){
        texts[0][0] = activityGame4x4Binding?.boardText00
        texts[0][1] = activityGame4x4Binding?.boardText01
        texts[0][2] = activityGame4x4Binding?.boardText02
        texts[0][3] = activityGame4x4Binding?.boardText03
        texts[1][0] = activityGame4x4Binding?.boardText10
        texts[1][1] = activityGame4x4Binding?.boardText11
        texts[1][2] = activityGame4x4Binding?.boardText12
        texts[1][3] = activityGame4x4Binding?.boardText13
        texts[2][0] = activityGame4x4Binding?.boardText20
        texts[2][1] = activityGame4x4Binding?.boardText21
        texts[2][2] = activityGame4x4Binding?.boardText22
        texts[2][3] = activityGame4x4Binding?.boardText23
        texts[3][0] = activityGame4x4Binding?.boardText30
        texts[3][1] = activityGame4x4Binding?.boardText31
        texts[3][2] = activityGame4x4Binding?.boardText32
        texts[3][3] = activityGame4x4Binding?.boardText33
    }

    private fun bindTexts5x5Layer(){
        texts[0][0] = activityGame5x5Binding?.boardText00
        texts[0][1] = activityGame5x5Binding?.boardText01
        texts[0][2] = activityGame5x5Binding?.boardText02
        texts[0][3] = activityGame5x5Binding?.boardText03
        texts[0][4] = activityGame5x5Binding?.boardText04
        texts[1][0] = activityGame5x5Binding?.boardText10
        texts[1][1] = activityGame5x5Binding?.boardText11
        texts[1][2] = activityGame5x5Binding?.boardText12
        texts[1][3] = activityGame5x5Binding?.boardText13
        texts[1][4] = activityGame5x5Binding?.boardText14
        texts[2][0] = activityGame5x5Binding?.boardText20
        texts[2][1] = activityGame5x5Binding?.boardText21
        texts[2][2] = activityGame5x5Binding?.boardText22
        texts[2][3] = activityGame5x5Binding?.boardText23
        texts[2][4] = activityGame5x5Binding?.boardText24
        texts[3][0] = activityGame5x5Binding?.boardText30
        texts[3][1] = activityGame5x5Binding?.boardText31
        texts[3][2] = activityGame5x5Binding?.boardText32
        texts[3][3] = activityGame5x5Binding?.boardText33
        texts[3][4] = activityGame5x5Binding?.boardText34
        texts[4][0] = activityGame5x5Binding?.boardText40
        texts[4][1] = activityGame5x5Binding?.boardText41
        texts[4][2] = activityGame5x5Binding?.boardText42
        texts[4][3] = activityGame5x5Binding?.boardText43
        texts[4][4] = activityGame5x5Binding?.boardText44
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}