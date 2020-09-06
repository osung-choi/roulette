package com.example.roulette.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roulette.R
import com.example.roulette.repository.database.entity.RouletteItem
import kotlinx.android.synthetic.main.adapter_setting_item.view.*
import java.util.*

class SettingItemAdpater(
    private val dragListener: ItemMoveSwifeCallback.ItemDragListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffUtil = AsyncListDiffer(this, DiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_setting_item, parent, false))

    override fun getItemCount() = diffUtil.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemHolder).bind(diffUtil.currentList[holder.adapterPosition])
    }

    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: RouletteItem) {
            itemView.itemName.text = item.name

            itemView.itemDragAndDrop.setOnTouchListener { view, motionEvent ->
                when(motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        dragListener.onStartDrag(this)
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                    }
                }

                false
            }
        }
    }

    fun changeItems(items: ArrayList<RouletteItem>) {
        arrayListOf<RouletteItem>().run {
            this.addAll(items)
            diffUtil.submitList(this)
        }
    }


    inner class DiffCallback: DiffUtil.ItemCallback<RouletteItem>() {
        override fun areItemsTheSame(oldItem: RouletteItem, newItem: RouletteItem)
                = oldItem.seq == newItem.seq

        override fun areContentsTheSame(oldItem: RouletteItem, newItem: RouletteItem)
                = oldItem.name == newItem.name
    }
}