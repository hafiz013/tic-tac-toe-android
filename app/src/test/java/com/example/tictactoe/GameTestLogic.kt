package com.example.tictactoe

import com.example.tictactoe.setting.TicTacToeLogic
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GameTestLogic {
    @Test
    fun `testGameWithPlayerSymbolXWith3x3GameSizeBoard`(){
        val gameLogic = TicTacToeLogic(3)
        gameLogic.makeMove(0, 0, 'X')
        gameLogic.makeMove(1, 1, 'X')
        gameLogic.makeMove(2, 2, 'X')
        assertEquals('X', gameLogic.checkResult())
    }

    @Test
    fun `testGameWithPlayerSymbolOWith3x3GameSizeBoard`(){
        val gameLogic = TicTacToeLogic(3)
        gameLogic.makeMove(0, 0, 'O')
        gameLogic.makeMove(1, 1, 'O')
        gameLogic.makeMove(2, 2, 'O')
        assertEquals('O', gameLogic.checkResult())
    }

    @Test
    fun `testGameWithPlayerIsDrawWith3x3GameSizeBoard`(){
        val gameLogic = TicTacToeLogic(3)
        gameLogic.makeMove(0, 0, 'X')
        gameLogic.makeMove(0, 1, 'O')
        gameLogic.makeMove(0, 2, 'X')
        gameLogic.makeMove(1, 0, 'X')
        gameLogic.makeMove(1, 1, 'O')
        gameLogic.makeMove(1, 2, 'O')
        gameLogic.makeMove(2, 0, 'O')
        gameLogic.makeMove(2, 1, 'X')
        gameLogic.makeMove(2, 2, 'X')
        assertEquals('D', gameLogic.checkResult())
    }

    @Test
    fun `testGameWithPlayerSymbolXWith4x4GameSizeBoard`(){
        val gameLogic = TicTacToeLogic(4)
        gameLogic.makeMove(0, 0, 'X')
        gameLogic.makeMove(1, 1, 'X')
        gameLogic.makeMove(2, 2, 'X')
        gameLogic.makeMove(3, 3, 'X')
        assertEquals('X', gameLogic.checkResult())
    }

    @Test
    fun `testGameWithPlayerSymbolOWith4x4GameSizeBoard`(){
        val gameLogic = TicTacToeLogic(4)
        gameLogic.makeMove(0, 0, 'O')
        gameLogic.makeMove(1, 1, 'O')
        gameLogic.makeMove(2, 2, 'O')
        gameLogic.makeMove(3, 3, 'O')
        assertEquals('O', gameLogic.checkResult())
    }
    @Test
    fun `testGameWithPlayerIsDrawWith4x4GameSizeBoard`(){
        val gameLogic = TicTacToeLogic(4)
        gameLogic.makeMove(0, 0, 'X')
        gameLogic.makeMove(0, 1, 'O')
        gameLogic.makeMove(0, 2, 'X')
        gameLogic.makeMove(0, 3, 'O')
        gameLogic.makeMove(1, 0, 'X')
        gameLogic.makeMove(1, 1, 'O')
        gameLogic.makeMove(1, 2, 'O')
        gameLogic.makeMove(1, 3, 'X')
        gameLogic.makeMove(2, 0, 'O')
        gameLogic.makeMove(2, 1, 'X')
        gameLogic.makeMove(2, 2, 'X')
        gameLogic.makeMove(2, 3, 'O')
        gameLogic.makeMove(3, 0, 'O')
        gameLogic.makeMove(3, 1, 'X')
        gameLogic.makeMove(3, 2, 'X')
        gameLogic.makeMove(3, 3, 'O')
        assertEquals('D', gameLogic.checkResult())
    }

    @Test
    fun `testGameWithPlayerSymbolXWith5x5GameSizeBoard`(){
        val gameLogic = TicTacToeLogic(5)
        gameLogic.makeMove(0, 0, 'X')
        gameLogic.makeMove(1, 1, 'X')
        gameLogic.makeMove(2, 2, 'X')
        gameLogic.makeMove(3, 3, 'X')
        gameLogic.makeMove(4, 4, 'X')
        assertEquals('X', gameLogic.checkResult())
    }

    @Test
    fun `testGameWithPlayerSymbolOWith5x5GameSizeBoard`(){
        val gameLogic = TicTacToeLogic(5)
        gameLogic.makeMove(0, 0, 'O')
        gameLogic.makeMove(1, 1, 'O')
        gameLogic.makeMove(2, 2, 'O')
        gameLogic.makeMove(3, 3, 'O')
        gameLogic.makeMove(4, 4, 'O')
        assertEquals('O', gameLogic.checkResult())
    }

    @Test
    fun `testGameWithPlayerIsDrawWith5x5GameSizeBoard`(){
        val gameLogic = TicTacToeLogic(5)
        gameLogic.makeMove(0, 0, 'X')
        gameLogic.makeMove(0, 1, 'O')
        gameLogic.makeMove(0, 2, 'X')
        gameLogic.makeMove(0, 3, 'O')
        gameLogic.makeMove(0, 4, 'X')
        gameLogic.makeMove(1, 0, 'X')
        gameLogic.makeMove(1, 1, 'O')
        gameLogic.makeMove(1, 2, 'O')
        gameLogic.makeMove(1, 3, 'X')
        gameLogic.makeMove(1, 4, 'O')
        gameLogic.makeMove(2, 0, 'O')
        gameLogic.makeMove(2, 1, 'X')
        gameLogic.makeMove(2, 2, 'X')
        gameLogic.makeMove(2, 3, 'O')
        gameLogic.makeMove(2, 4, 'X')
        gameLogic.makeMove(3, 0, 'O')
        gameLogic.makeMove(3, 1, 'X')
        gameLogic.makeMove(3, 2, 'X')
        gameLogic.makeMove(3, 3, 'O')
        gameLogic.makeMove(3, 4, 'X')
        gameLogic.makeMove(4, 0, 'O')
        gameLogic.makeMove(4, 1, 'X')
        gameLogic.makeMove(4, 2, 'X')
        gameLogic.makeMove(4, 3, 'O')
        gameLogic.makeMove(4, 4, 'X')
        assertEquals('D', gameLogic.checkResult())
    }
}