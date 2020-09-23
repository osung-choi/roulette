package com.example.roulette.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myslotmachine.SlotMachineView
import com.example.roulette.adapter.SaveDataAdapter
import com.example.roulette.customview.RouletteView
import com.example.roulette.repository.database.entity.Roulette
import com.example.roulette.repository.database.entity.RouletteItem

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("setRouletteItem")
    fun setRouletteItem(rouletteView: RouletteView, items: ArrayList<RouletteItem>) {
        rouletteView.initMenu(items)
        rouletteView.invalidate()
    }

    @JvmStatic
    @BindingAdapter("angle", "resultListener")
    fun startRotate(rouletteView: RouletteView, angle: Float, listener: (String) -> Unit) {
        rouletteView.setEndCallbackListener(listener)
        rouletteView.startDefaultRotate(angle)
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun setVisible(view: View, isVisible: Boolean) {
        if(isVisible) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }

    @JvmStatic
    @BindingAdapter("bindSaveData")
    fun setVisible(recyclerView: RecyclerView, items: List<Roulette>) {
        val adapter = recyclerView.adapter as SaveDataAdapter

        arrayListOf<Roulette>().run {
            this.addAll(items)
            adapter.diffUtil.submitList(this)
        }
    }

    @JvmStatic
    @BindingAdapter("setSlotMachineItem")
    fun setSlotMachineItem(slotMachineView: SlotMachineView, list: ArrayList<RouletteItem>) {
        val nameList = arrayListOf<String>().apply {
            list.forEach {
                add(it.name)
            }
        }
        slotMachineView.setSlotList(nameList)
    }

    @JvmStatic
    @BindingAdapter("setSlotResultCallback")
    fun setSlotResultCallback(slotMachineView: SlotMachineView, callback: (String) -> Unit) {
        slotMachineView.setResultCallbackListener(callback)
    }
}