package com.example.roulette.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.roulette.R
import com.example.roulette.customview.MyActionBar
import com.example.roulette.databinding.ActivitySlotMachineBinding
import com.example.roulette.repository.database.entity.RouletteItem
import com.example.roulette.viewmodel.SlotMachineViewModel
import kotlinx.android.synthetic.main.activity_slot_machine.*
import java.util.*

class SlotMachineActivity : AppCompatActivity() {
    companion object {
        private const val INTENT_ROULETTE_ITEM = "intent_roulette_item"

        fun intent(context: Context, items: ArrayList<RouletteItem>) = Intent(context, SlotMachineActivity::class.java).apply {
            putExtra(INTENT_ROULETTE_ITEM, items)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(SlotMachineViewModel::class.java)

        val binding = DataBindingUtil.setContentView<ActivitySlotMachineBinding>(this, R.layout.activity_slot_machine)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        (intent.getSerializableExtra(INTENT_ROULETTE_ITEM) as ArrayList<RouletteItem>).also {
            viewModel.setSlotItem(it)
        }


        viewModel.result.observe(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.startMachine.observe(this, Observer {
            slotMachineView.startSlotMachine()
        })

        MyActionBar(this, supportActionBar).run {
            setActionBar("슬롯머신")
        }
    }
}