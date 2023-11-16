package com.example.tictactoe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tictactoe.adapter.HistoryAdapter
import com.example.tictactoe.base.BaseActivity
import com.example.tictactoe.databinding.ActivityHistoryPlayingBinding
import com.example.tictactoe.model.Player
import com.example.tictactoe.viewmodel.HistoryGamePlayImpl
import com.example.tictactoe.viewmodel.HistoryGamePlayViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HistoryPlaying : BaseActivity<ActivityHistoryPlayingBinding>() {
    private val historyGamePlayImpl:HistoryGamePlayImpl by inject()
    private var adapter: HistoryAdapter? = null
    companion object{
        fun newIntent(context: Context){
            val intent = Intent(context, HistoryPlaying::class.java)
            context.startActivity(intent)
        }
    }

    override fun createViewBinding(): ActivityHistoryPlayingBinding {
        return ActivityHistoryPlayingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getListOfHistoryPlaying()
    }

    private fun getListOfHistoryPlaying(){
        historyGamePlayImpl.getHistoryGamePlay()

        lifecycleScope.launch {
            historyGamePlayImpl.uiState.collect{
                when(it){
                    is HistoryGamePlayViewModel.HistoryGamePlayUiState.Loading -> {
                        showProgressDialog()
                        binding.noHistory.visibility = View.GONE
                        binding.list.visibility = View.GONE
                    }
                    is HistoryGamePlayViewModel.HistoryGamePlayUiState.Success -> {
                        hideProgressDialog()
                        binding.noHistory.visibility = View.GONE
                        binding.list.visibility = View.VISIBLE
                        showList(it.player)
                    }else -> {
                        hideProgressDialog()
                        binding.noHistory.visibility = View.VISIBLE
                        binding.list.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun showList(player: List<Player>) {
        val headerData = listOf(resources.getString(R.string.id_player), resources.getString(R.string.player_name),
            resources.getString(R.string.scoring_player), resources.getString(R.string.win_rate_player) )
        adapter = HistoryAdapter(headerData, player)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)
    }
}