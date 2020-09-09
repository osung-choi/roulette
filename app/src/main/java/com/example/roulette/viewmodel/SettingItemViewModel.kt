package com.example.roulette.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.roulette.SingleLiveEvent
import com.example.roulette.repository.*
import com.example.roulette.repository.database.entity.Roulette
import com.example.roulette.repository.database.entity.RouletteItem
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList


class SettingItemViewModel(application: Application) : AndroidViewModel(application) {
    private val _items = MutableLiveData<ArrayList<RouletteItem>>().apply {
        this.value = arrayListOf()
    }
    private val dbRepo = DataAccessRepo(application)
    val items: LiveData<ArrayList<RouletteItem>> = _items

    private val _itemDialog = SingleLiveEvent<Any>()
    val itemDialog: LiveData<Any> = _itemDialog

    private val _updateItems = MutableLiveData<Boolean>().apply { this.value = false }
    val updateItems: LiveData<Boolean> = _updateItems

    private val _startRoulette = SingleLiveEvent<ArrayList<RouletteItem>>()
    val startRoulette: LiveData<ArrayList<RouletteItem>> = _startRoulette

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val compositeDisposable = CompositeDisposable()
    private val backupItems = arrayListOf<RouletteItem>()
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
            _error.value = "최소 ${MIN_ITEM_COUNT}개의 아이템을 등록하세요."
        }
    }

    fun addNewItem(name: String) {
        RouletteItem(++idx,0, name)
            .run {
            _items += this
        }
        compareBackupItem()
    }

    fun onItemMove(fromPos: Int, targetPos: Int) {
        _items.swap(fromPos, targetPos)
        compareBackupItem()
    }

    fun onItemDismiss(pos: Int) {
        _items.itemDelete(pos)
        compareBackupItem()
    }

    fun saveRouletteData() {
        val title = Utils.makeRouletteTitle(_items.value!!)
        val saveRoulette = Roulette(0, title)

        compositeDisposable.add(
            Observable.just(saveRoulette)
                .subscribeOn(Schedulers.io())
                .map { dbRepo.insertRoulette(it) }
                .map { seq ->
                    val list = setRouletteItemSeq(seq)
                    dbRepo.insertRouletteItems(list)
                }.subscribe()
        )
    }

    fun selectRouletteItem(seq: Int) {
        val selectList =  dbRepo.selectRouletteItem(seq)
        val selectMaxSeq = dbRepo.selectMaxItemSeq()

        compositeDisposable.add(
            selectList.zipWith(selectMaxSeq, BiFunction { list: List<RouletteItem>, maxSeq: Int ->
                idx = maxSeq
                list
            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    backupItems.addAll(it)
                    _items.addAll(it)
                }
        )
    }

    private fun compareBackupItem() {
        _updateItems.value = backupItems.nameNotEquals(_items.value!!)
    }

    private fun setRouletteItemSeq(seq: Long): ArrayList<RouletteItem> {
        val list = _items.value!!
        list.forEach {
            it.seq = 0
            it.rouletteSeq = seq.toInt()
        }

        return list
    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }


}

