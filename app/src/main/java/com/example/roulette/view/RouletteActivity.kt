package com.example.roulette.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import com.example.roulette.R
import com.example.roulette.customview.MyActionBar
import com.example.roulette.databinding.ActivityRouletteBinding
import com.example.roulette.repository.database.entity.RouletteItem
import com.example.roulette.viewmodel.RouletteViewModel

class RouletteActivity : AppCompatActivity() {
    companion object {
        private const val INTENT_ROULETTE_ITEM = "intent_roulette_item"
        fun intent(context: Context, items: ArrayList<RouletteItem>) = Intent(context, RouletteActivity::class.java).apply {
            putExtra(INTENT_ROULETTE_ITEM, items)
        }
    }

    private lateinit var viewModel: RouletteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = setContentView<ActivityRouletteBinding>(this,
            R.layout.activity_roulette
        )
        viewModel = ViewModelProvider(this).get(RouletteViewModel::class.java)
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