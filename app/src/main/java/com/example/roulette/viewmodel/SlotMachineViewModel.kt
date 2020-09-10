package com.example.roulette.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roulette.SingleLiveEvent

class SlotMachineViewModel: ViewModel() {
    private val _slotItem = MutableLiveData<ArrayList<String>>().apply {
        value = arrayListOf("몬스터","면봉","아이패드","애플펜슬","필통","선풍기","모니터","받침대","컵","물통","알약","환풍기","창문","소나기","파티션","휴지")
    }

    val slotItem: LiveData<ArrayList<String>> = _slotItem

    private val _result = MutableLiveData<String>()
    val result : LiveData<String> = _result

    private val _startMachine = SingleLiveEvent<Any>()
    val startMachine : LiveData<Any> = _startMachine

    fun resultCallbackListener(): (String) -> Unit = {
        _result.value = it
    }
    fun startSlotMachine() {
        _startMachine.value = Any()
    }
}