package com.example.tictactoe.setting

import java.util.Arrays

class TicTacToeLogic(gameSize:Int) {
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
        if (lastMoveX == 0) {
            if (lastMoveY == 0) {
                if (board[0][1] == symbol && board[0][2] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(0, 2))
                    return true
                } else if (board[1][0] == symbol && board[2][0] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 0), intArrayOf(1, 0), intArrayOf(2, 0))
                    return true
                } else if (board[1][1] == symbol && board[2][2] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 0), intArrayOf(1, 1), intArrayOf(2, 2))
                    return true
                }
            } else if (lastMoveY == 1) {
                if (board[0][0] == symbol && board[0][2] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(0, 2))
                    return true
                } else if (board[1][1] == symbol && board[2][1] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 1), intArrayOf(1, 1), intArrayOf(2, 1))
                    return true
                }
            } else if (lastMoveY == 2) {
                if (board[0][0] == symbol && board[0][1] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(0, 2))
                    return true
                } else if (board[1][2] == symbol && board[2][2] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 2), intArrayOf(1, 2), intArrayOf(2, 2))
                    return true
                } else if (board[1][1] == symbol && board[2][0] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 2), intArrayOf(1, 1), intArrayOf(2, 0))
                    return true
                }
            }
        } else if (lastMoveX == 1) {
            if (lastMoveY == 0) {
                if (board[1][1] == symbol && board[1][2] == symbol) {
                    winPositions = arrayOf(intArrayOf(1, 0), intArrayOf(1, 1), intArrayOf(1, 2))
                    return true
                } else if (board[0][0] == symbol && board[2][0] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 0), intArrayOf(1, 0), intArrayOf(2, 0))
                    return true
                }
            } else if (lastMoveY == 1) {
                if (board[0][1] == symbol && board[2][1] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 1), intArrayOf(1, 1), intArrayOf(2, 1))
                    return true
                } else if (board[1][0] == symbol && board[1][2] == symbol) {
                    winPositions = arrayOf(intArrayOf(1, 0), intArrayOf(1, 1), intArrayOf(1, 2))
                    return true
                } else if (board[0][0] == symbol && board[2][2] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 0), intArrayOf(1, 1), intArrayOf(2, 2))
                    return true
                } else if (board[0][2] == symbol && board[2][0] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 2), intArrayOf(1, 1), intArrayOf(2, 0))
                    return true
                }
            } else if (lastMoveY == 2) {
                if (board[1][0] == symbol && board[1][1] == symbol) {
                    winPositions = arrayOf(intArrayOf(1, 0), intArrayOf(1, 1), intArrayOf(1, 2))
                    return true
                } else if (board[0][2] == symbol && board[2][2] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 2), intArrayOf(1, 2), intArrayOf(2, 2))
                    return true
                }
            }
        } else if (lastMoveX == 2) {
            if (lastMoveY == 0) {
                if (board[0][0] == symbol && board[1][0] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 0), intArrayOf(1, 0), intArrayOf(2, 0))
                    return true
                } else if (board[2][1] == symbol && board[2][2] == symbol) {
                    winPositions = arrayOf(intArrayOf(2, 0), intArrayOf(2, 1), intArrayOf(2, 2))
                    return true
                } else if (board[1][1] == symbol && board[0][2] == symbol) {
                    winPositions = arrayOf(intArrayOf(2, 0), intArrayOf(1, 1), intArrayOf(0, 2))
                    return true
                }
            } else if (lastMoveY == 1) {
                if (board[2][0] == symbol && board[2][2] == symbol) {
                    winPositions = arrayOf(intArrayOf(2, 0), intArrayOf(2, 1), intArrayOf(2, 2))
                    return true
                } else if (board[0][1] == symbol && board[1][1] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 1), intArrayOf(1, 1), intArrayOf(2, 1))
                    return true
                }
            } else if (lastMoveY == 2) {
                if (board[0][2] == symbol && board[1][2] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 2), intArrayOf(1, 2), intArrayOf(2, 2))
                    return true
                } else if (board[2][0] == symbol && board[2][1] == symbol) {
                    winPositions = arrayOf(intArrayOf(2, 0), intArrayOf(2, 1), intArrayOf(2, 2))
                    return true
                } else if (board[0][0] == symbol && board[1][1] == symbol) {
                    winPositions = arrayOf(intArrayOf(0, 0), intArrayOf(1, 1), intArrayOf(2, 2))
                    return true
                }
            }
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