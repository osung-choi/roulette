package com.example.roulette.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roulette.SingleLiveEvent
import com.example.roulette.repository.database.entity.RouletteItem

class SlotMachineViewModel: ViewModel() {
    private val _slotItem = MutableLiveData<ArrayList<RouletteItem>>().apply {
        value = arrayListOf()
    }

    val slotItem: LiveData<ArrayList<RouletteItem>> = _slotItem

    private val _result = MutableLiveData<String>()
    val result : LiveData<String> = _result

    private val _startMachine = SingleLiveEvent<Any>()
    val startMachine : LiveData<Any> = _startMachine

    fun setSlotItem(items: ArrayList<RouletteItem>) {
        _slotItem.value = items
    }

    fun resultCallbackListener(): (String) -> Unit = {
        _result.value = it
    }
    fun startSlotMachine() {
        _startMachine.value = Any()
    }
}