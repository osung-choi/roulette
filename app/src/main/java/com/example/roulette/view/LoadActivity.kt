package com.example.roulette.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roulette.R
import com.example.roulette.customview.MyActionBar
import kotlinx.android.synthetic.main.activity_load.*

class LoadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        
        MyActionBar(this, supportActionBar).run {
            setActionBar("선택")
        }

        loadSaveRoulette.setOnClickListener {

        }

        loadNewRoulette.setOnClickListener {
            startActivity(SettingItemActivity.intent(this))
        }
    }
}