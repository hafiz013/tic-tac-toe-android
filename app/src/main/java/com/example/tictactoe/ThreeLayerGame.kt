package com.example.tictactoe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ThreeLayerGame : AppCompatActivity() {
    companion object{
        private val PLAYER_1 = "PLAYER_1"
        private val PLAYER_2 = "PLAYER_2"
        fun newIntent(context: Context, playerName1:String, playerName2:String){
            val intent = Intent(context, ThreeLayerGame::class.java)
            intent.putExtra(PLAYER_1, playerName1)
            intent.putExtra(PLAYER_2, playerName2)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_three_layer_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}