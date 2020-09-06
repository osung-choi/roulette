package com.example.roulette.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roulette.R
import com.example.roulette.adapter.ItemMoveSwipeCallback
import com.example.roulette.adapter.SettingItemAdpater
import com.example.roulette.customview.MyActionBar
import com.example.roulette.databinding.ActivitySettingItemBinding
import com.example.roulette.view.dialog.EditDialog
import com.example.roulette.view.dialog.MessageDialog
import com.example.roulette.viewmodel.SettingItemViewModel
import kotlinx.android.synthetic.main.activity_setting_item.*

class SettingItemActivity : AppCompatActivity(),
    ItemMoveSwipeCallback.ItemTouchHelperAdapter,
    ItemMoveSwipeCallback.ItemDragListener{
    companion object {
        fun intent(context: Context) = Intent(context, SettingItemActivity::class.java)
    }

    private lateinit var viewModel: SettingItemViewModel
    private val mAdapter = SettingItemAdpater(this)
    private var touchHelper: ItemTouchHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivitySettingItemBinding>(
            this,
            R.layout.activity_setting_item
        )
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(SettingItemViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initUi()
        initEvent()
    }

    private fun initUi() {
        MyActionBar(this, supportActionBar).run {
            setActionBar("항목 구성")
        }

        val callback: ItemTouchHelper.Callback = ItemMoveSwipeCallback(this)
        touchHelper = ItemTouchHelper(callback)
        touchHelper!!.attachToRecyclerView(itemList)

        itemList.layoutManager = LinearLayoutManager(this)
        itemList.adapter = mAdapter
    }

    private fun initEvent() {
        viewModel.error.observe(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.itemDialog.observe(this, Observer {
            EditDialog(this) { itemName ->
                viewModel.addNewItem(itemName)
            }.setTitle("추가")
               .setYesContent("추가")
                .setNoContent("취소")
                .show()
        })

        viewModel.items.observe(this, Observer {
            mAdapter.changeItems(it)
        })

        viewModel.startRoulette.observe(this, Observer {
            MessageDialog(this) { save ->
                if(save) {
                    viewModel.saveRouletteData()
                }

                startActivity(MainActivity.intent(this, it))
            }.setTitle("저장")
                .setMessage("룰렛을 저장하시겠습니까?")
                .setYesContent("저장")
                .setNoContent("저장하지 않고 시작")
                .show()
        })
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper?.startDrag(viewHolder)
    }

    override fun onItemMove(fromPos: Int, targetPos: Int) {
        viewModel.onItemMove(fromPos, targetPos)
    }

    override fun onItemDismiss(pos: Int) {
        viewModel.onItemDismiss(pos)
    }
}