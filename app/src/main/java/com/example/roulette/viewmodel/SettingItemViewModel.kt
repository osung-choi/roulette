package com.example.roulette.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roulette.SingleLiveEvent
import com.example.roulette.repository.*
import com.example.roulette.repository.data.RouletteItem
import java.util.*
import kotlin.collections.ArrayList

class SettingItemViewModel: ViewModel() {
    private val _items = MutableLiveData<ArrayList<RouletteItem>>().apply {
        this.value = arrayListOf()
    }
    val items: LiveData<ArrayList<RouletteItem>> = _items

    private val _itemDialog = SingleLiveEvent<Any>()
    val itemDialog: LiveData<Any> = _itemDialog

    private val _startRoulette = SingleLiveEvent<ArrayList<RouletteItem>>()
    val startRoulette: LiveData<ArrayList<RouletteItem>> = _startRoulette

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var idx = 0

    fun addItemClick() {
        if(_items.checkMaxSize()) {
            _itemDialog.call()
        }else {
            _error.value = "설정 가능한 최대 갯수는 ${MAX_ITEM_COUNT}개 입니다."
        }
    }

    fun startClick() {
        if(_items.checkMinSize()) {
            _startRoulette.value = _items.value
        }else {
            _error.value = "최소 ${MIN_ITEM_COUNT + 1}개의 아이템을 등록하세요."
        }
    }

    fun addNewItem(name: String) {
        RouletteItem(idx++, name).run {
            _items += this
        }
    }

    fun onItemMove(fromPos: Int, targetPos: Int) {
        _items.swap(fromPos, targetPos)
    }

    fun onItemDismiss(pos: Int) {
        _items.itemDelete(pos)
    }
}

