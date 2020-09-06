package com.example.roulette.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemMoveSwifeCallback(
    itemTouchHelperAdapter: ItemTouchHelperAdapter
): ItemTouchHelper.Callback() {
    private val itemTouchHelperAdapter = itemTouchHelperAdapter


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val flagDrag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val flagSwipe = ItemTouchHelper.START or ItemTouchHelper.END

        return makeMovementFlags(flagDrag, flagSwipe)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        itemTouchHelperAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        itemTouchHelperAdapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun isLongPressDragEnabled() = false

    interface ItemTouchHelperAdapter {
        fun onItemMove(fromPos: Int, targetPos: Int)
        fun onItemDismiss(pos: Int)
    }

    interface ItemDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }
}