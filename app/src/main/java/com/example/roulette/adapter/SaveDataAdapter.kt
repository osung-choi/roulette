package com.example.roulette.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roulette.databinding.AdapterSaveDataBinding
import com.example.roulette.repository.database.entity.Roulette

class SaveDataAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val diffUtil = AsyncListDiffer(this, DiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(AdapterSaveDataBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = diffUtil.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bind(diffUtil.currentList[holder.adapterPosition])
    }

    private inner class MyViewHolder(
        private val binding: AdapterSaveDataBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Roulette) {
            binding.item = item
        }
    }

    private inner class DiffCallback: DiffUtil.ItemCallback<Roulette>() {
        override fun areItemsTheSame(oldItem: Roulette, newItem: Roulette)
                = oldItem.seq == newItem.seq

        override fun areContentsTheSame(oldItem: Roulette, newItem: Roulette)
                = oldItem.title == newItem.title
    }
}