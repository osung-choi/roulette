package com.example.roulette.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roulette.R
import com.example.roulette.adapter.ItemMoveSwipeCallback
import com.example.roulette.adapter.SaveDataAdapter
import com.example.roulette.customview.MyActionBar
import com.example.roulette.databinding.ActivitySavedDataBinding
import com.example.roulette.view.dialog.MessageDialog
import com.example.roulette.viewmodel.SavedDataViewModel
import kotlinx.android.synthetic.main.activity_saved_data.*
import kotlinx.android.synthetic.main.activity_setting_item.*

class SavedDataActivity : AppCompatActivity(),
    ItemMoveSwipeCallback.ItemTouchHelperAdapter
{
    companion object {
        fun intent(context: Context) = Intent(context, SavedDataActivity::class.java)
    }

    lateinit var viewModel: SavedDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(SavedDataViewModel::class.java)

        val binding = DataBindingUtil.setContentView<ActivitySavedDataBinding>(this, R.layout.activity_saved_data)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initUi()
        initEvent()

        viewModel.loadSaveData()
    }

    private fun initUi() {
        MyActionBar(this, supportActionBar).run {
            setActionBar("저장된 룰렛")
        }

        listSaveData.layoutManager = LinearLayoutManager(this)
        listSaveData.adapter = SaveDataAdapter()

        val callback: ItemTouchHelper.Callback = ItemMoveSwipeCallback(this)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(listSaveData)
    }

    private fun initEvent() {

    }

    override fun onItemMove(fromPos: Int, targetPos: Int) {

    }

    override fun onItemDismiss(pos: Int) {
        MessageDialog(this) {
            if(it) viewModel.onItemDismiss(pos)
            else listSaveData.adapter!!.notifyItemChanged(pos)
        }.setTitle("삭제")
            .setMessage("해당 룰렛을 삭제하시겠습니까?")
            .setYesContent("삭제")
            .setNoContent("취소")
            .show()
    }
}