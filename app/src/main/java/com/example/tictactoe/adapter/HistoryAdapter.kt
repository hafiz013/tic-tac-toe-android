package com.example.tictactoe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoe.databinding.ItemHeaderItemBinding
import com.example.tictactoe.databinding.ItemRowItemBinding
import com.example.tictactoe.model.Player

class HistoryAdapter(private val headerData: List<String>, private val rowList: List<Player>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val HEADER_VIEW_TYPE = 0
    private val ROW_VIEW_TYPE = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                val binding = ItemHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }
            ROW_VIEW_TYPE -> {
                val binding = ItemRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RowViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return rowList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.binding.headerColumn1TextView.text = headerData[0]
                holder.binding.headerColumn2TextView.text = headerData[1]
                holder.binding.headerColumn3TextView.text = headerData[2]
                holder.binding.headerColumn4TextView.text = headerData[3]
            }
            is RowViewHolder -> {
                val currentItem = rowList[position - 1] // Subtract 1 to account for the header
                holder.binding.rowColumn1TextView.text = currentItem.id.toString()
                holder.binding.rowColumn2TextView.text = currentItem.playerName
                holder.binding.rowColumn3TextView.text = currentItem.score.toString()
                holder.binding.rowColumn4TextView.text = currentItem.percentageWin.toString()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER_VIEW_TYPE else ROW_VIEW_TYPE
    }

    inner class HeaderViewHolder(val binding: ItemHeaderItemBinding) : RecyclerView.ViewHolder(binding.root) {}
    inner class RowViewHolder(val binding: ItemRowItemBinding) : RecyclerView.ViewHolder(binding.root) {}
}
