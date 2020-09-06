package com.example.roulette.repository

import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.collections.ArrayList

const val MAX_ITEM_COUNT = 12
const val MIN_ITEM_COUNT = 1

fun<T> MutableLiveData<ArrayList<T>>.checkMaxSize(): Boolean {
    val list = this.value ?: arrayListOf()
    return list.size < MAX_ITEM_COUNT
}

fun<T> MutableLiveData<ArrayList<T>>.checkMinSize(): Boolean {
    val list = this.value ?: arrayListOf()
    return list.size > MIN_ITEM_COUNT
}

fun<T> MutableLiveData<ArrayList<T>>.swap(fromPos: Int, targetPos: Int) {
    val list = this.value
    if(list != null && list.size > fromPos && list.size > targetPos) {
        if(fromPos < targetPos) {
            for(i in fromPos until targetPos) {
                Collections.swap(list, i, i+1)
            }
        }else {
            for (i in fromPos downTo targetPos + 1 ) {
                Collections.swap(list, i, i-1)
            }
        }

        this.value = list
    }
}

fun<T> MutableLiveData<ArrayList<T>>.itemDelete(pos: Int) {
    val list = this.value
    if(list != null && list.size > pos) {
        list.removeAt(pos)
        this.value = list
    }
}

operator fun<T> MutableLiveData<ArrayList<T>>.plusAssign(item: T) {
    val list = this.value ?: arrayListOf()
    list.add(item)
    this.value = list
}

operator fun<T> MutableLiveData<ArrayList<T>>.minusAssign(index: Int) {
    if(!this.value.isNullOrEmpty()) {
        val list = this.value!!
        list.removeAt(index)
        this.value = list
    }
}