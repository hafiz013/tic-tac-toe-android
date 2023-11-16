package com.example.tictactoe

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


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

    fun onShareResultGame(v:View){
        val screenshot: Bitmap = captureScreenShot(binding.main)
        val uri = saveImage(screenshot)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setType("image/png")
        startActivity(intent)
    }

    private fun captureScreenShot(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas)
        else canvas.drawColor(ContextCompat.getColor(this, R.color.white))
        view.draw(canvas)
        return returnedBitmap
    }

    private fun saveImage(image: Bitmap): Uri? {
        val imagesFolder = File(cacheDir, "images")
        var uri: Uri? = null
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "shared_image.png")
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
            uri = FileProvider.getUriForFile(this, "com.example.tictactoe.fileprovider", file)
        } catch (e: IOException) {
            Log.d("HISTORY_PLAY", "IOException while trying to write file for sharing: " + e.message)
        }
        return uri
    }

}