package com.example.roulette.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.roulette.repository.DataAccessRepo
import com.example.roulette.repository.addAll
import com.example.roulette.repository.database.entity.Roulette
import com.example.roulette.repository.itemDelete
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class SavedDataViewModel(application: Application): AndroidViewModel(application) {
    private val _items = MutableLiveData<ArrayList<Roulette>>().also { it.value = arrayListOf() }
    val items : LiveData<ArrayList<Roulette>> = _items

    private val compositeDisposable = CompositeDisposable()
    private val dbRepo = DataAccessRepo(application)

    fun loadSaveData() {
        compositeDisposable.add(
            dbRepo.selectAllRoulette()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _items.addAll(it) }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }

    fun onItemDismiss(pos: Int) {
        _items.value?.let {
            compositeDisposable.add(
                Maybe.just(it[pos])
                    .map { item -> dbRepo.deleteRoulette(item) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { _items.itemDelete(pos) }
            )
        }
    }
}