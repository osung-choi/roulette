package com.example.roulette.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roulette.R
import com.example.roulette.customview.MyActionBar
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        
        MyActionBar(this, supportActionBar).run {
            setActionBar("선택")
        }

        btnSaveRoulette.setOnClickListener {
            startActivity(SavedDataActivity.intent(this))
        }

        btnNewRoulette.setOnClickListener {
            startActivity(SettingItemActivity.intent(this))
        }
    }
}