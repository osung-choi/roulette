package com.example.roulette.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.roulette.R
import com.example.roulette.databinding.ActivitySlotMachineBinding
import com.example.roulette.viewmodel.SlotMachineViewModel
import kotlinx.android.synthetic.main.activity_slot_machine.*

class SlotMachineActivity : AppCompatActivity() {
    companion object {
        fun intent(context: Context) = Intent(context, SlotMachineActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(SlotMachineViewModel::class.java)

        val binding = DataBindingUtil.setContentView<ActivitySlotMachineBinding>(this, R.layout.activity_slot_machine)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.result.observe(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.startMachine.observe(this, Observer {
            slotMachineView.startSlotMachine()
        })
    }
}