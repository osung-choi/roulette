package com.example.roulette.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roulette.SingleLiveEvent
import com.example.roulette.repository.Utils
import com.example.roulette.repository.database.entity.RouletteItem


class MainViewModel : ViewModel() {
    private val _menuItem = MutableLiveData<ArrayList<RouletteItem>>()
    val menuItem: LiveData<ArrayList<RouletteItem>> = _menuItem

    private val _fromAngle = SingleLiveEvent<Float>().apply { this.value = null }
    val fromAngle: LiveData<Float> = _fromAngle

    private val _showDim = MutableLiveData<Boolean>().apply { this.value = false }
    val showDim: LiveData<Boolean> = _showDim

    private val _showResult = MutableLiveData<String>()
    val showResult: LiveData<String> = _showResult


    val resultListener : (String) -> Unit = {
        _fromAngle.value = null
        _showDim.value = true
        _showResult.value = it
    }

    fun selectMenuItem(items: ArrayList<RouletteItem>) {
        _menuItem.value = items
    }

    fun startRouletteRotate() {
        _fromAngle.value = (Utils.getRandom(360) + 3600).toFloat()
    }

    fun resultLayoutClick() {
        _showDim.value = false
    }
}