package com.example.tictactoe

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.tictactoe.base.BaseActivity
import com.example.tictactoe.databinding.ActivitySetupGameBinding

class SetupGame : BaseActivity<ActivitySetupGameBinding>() {
    companion object{
        fun newIntent(context: Context){
            val intent = Intent(context, SetupGame::class.java)
            context.startActivity(intent)
        }
    }
    override fun createViewBinding(): ActivitySetupGameBinding {
        return ActivitySetupGameBinding.inflate(layoutInflater)
    }

    fun onPlayGame(v:View){
        val playerName1 = binding.player1NameEdit.text.toString()
        val playerName2 = binding.player2NameEdit.text.toString()

        if (playerName1.isEmpty()){
            Toast.makeText(this, resources.getString(R.string.player1_name), Toast.LENGTH_SHORT).show()
            return
        }else if(playerName2.isEmpty()){
            Toast.makeText(this, resources.getString(R.string.player2_name), Toast.LENGTH_SHORT).show()
            return
        }

        when(binding.gameModeGroup.checkedRadioButtonId){
            R.id.threeLayerBtn -> {
                GamePlay.newIntent(this, playerName1 = playerName1, playerName2 = playerName2, gameSize = 3)
            }
            R.id.fourLayerBtn -> {
                GamePlay.newIntent(this, playerName1 = playerName1, playerName2 = playerName2, gameSize = 4)
            }
            else -> {
                GamePlay.newIntent(this, playerName1 = playerName1, playerName2 = playerName2, gameSize = 5)
            }
        }
    }
}