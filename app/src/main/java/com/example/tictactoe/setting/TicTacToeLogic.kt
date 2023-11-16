package com.example.tictactoe.setting

import java.util.Arrays

class TicTacToeLogic(private val gameSize:Int) {
    private val MAX_MOVES_NUMBER = gameSize * gameSize
    private val board = Array(gameSize) { CharArray(gameSize) }
    private var lastMoveX = 0
    private var lastMoveY = 0
    private var moveCounter = 0
    private var winPositions: Array<IntArray>? = null

    init {
        initializeBoard()
    }
    private fun initializeBoard() {
        for (chars in board) {
            Arrays.fill(chars, ' ')
        }
    }

    fun makeMove(x: Int, y: Int, symbol: Char) {
        board[x][y] = symbol
        lastMoveX = x
        lastMoveY = y
        moveCounter++
    }

    fun undoMove(x: Int, y: Int) {
        board[x][y] = ' '
        moveCounter--
    }

    fun getWinPositions(): Array<IntArray>? {
        return winPositions
    }

    fun getLastMoveX(): Int {
        return lastMoveX
    }

    fun getLastMoveY(): Int {
        return lastMoveY
    }

    private fun checkWin(symbol: Char): Boolean {
        // Check rows and columns
        for (i in 0 until gameSize) {
            if (board[i].all { it == symbol } || (0 until gameSize).all { board[it][i] == symbol }) {
                winPositions = if (board[i].all { it == symbol }) {
                    Array(gameSize) { intArrayOf(i, it) }
                } else {
                    Array(gameSize) { intArrayOf(it, i) }
                }
                return true
            }
        }

        // Check diagonals
        if ((0 until gameSize).all { board[it][it] == symbol }) {
            winPositions = Array(gameSize) { intArrayOf(it, it) }
            return true
        }
        if ((0 until gameSize).all { board[it][gameSize - 1 - it] == symbol }) {
            winPositions = Array(gameSize) { intArrayOf(it, gameSize - 1 - it) }
            return true
        }

        return false
    }

    fun checkResult(): Char {
        var result = ' '
        if (board[lastMoveX][lastMoveY] == 'X') {
            if (checkWin('X')) result = 'X'
        } else if (board[lastMoveX][lastMoveY] == 'O') {
            if (checkWin('O')) result = 'O'
        }
        if (moveCounter == MAX_MOVES_NUMBER && result == ' ') result = 'D'
        return result
    }

    fun resetGame() {
        initializeBoard()
        moveCounter = 0
    }
}