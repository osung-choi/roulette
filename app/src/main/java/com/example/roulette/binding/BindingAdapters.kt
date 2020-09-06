package com.example.roulette.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.roulette.customview.RouletteView
import com.example.roulette.repository.data.RouletteItem

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
        rouletteView.startRatate(angle, listener)
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun setVisible(view: View, isVisible: Boolean) {
        if(isVisible) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }
}