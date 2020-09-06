package com.example.roulette.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import com.example.roulette.R
import com.example.roulette.customview.MyActionBar
import com.example.roulette.databinding.ActivityMainBinding
import com.example.roulette.repository.database.entity.RouletteItem
import com.example.roulette.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    companion object {
        private const val INTENT_ROULETTE_ITEM = "intent_roulette_item"
        fun intent(context: Context, items: ArrayList<RouletteItem>) = Intent(context, MainActivity::class.java).apply {
            putExtra(INTENT_ROULETTE_ITEM, items)
        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        @Suppress("UNCHECKED_CAST")
        (intent.getSerializableExtra(INTENT_ROULETTE_ITEM) as ArrayList<RouletteItem>).apply {
            viewModel.selectMenuItem(this)
        }

        MyActionBar(this, supportActionBar).run {
            setActionBar("룰렛")
        }
    }
}