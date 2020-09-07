package com.example.roulette.customview

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.example.roulette.R
import kotlinx.android.synthetic.main.layout_custom_actionbar.view.*

class MyActionBar(activity: Activity, actionBar: ActionBar?) {
    private val activity = activity
    private val actionBar = actionBar
    private lateinit var mCustomView: View

    fun setActionBar(title: String) {
        actionBar?.let { actionBar ->
            actionBar.setDisplayShowCustomEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayShowHomeEnabled(false)

            mCustomView = LayoutInflater.from(activity)
                .inflate(R.layout.layout_custom_actionbar, null)

            mCustomView.title.text = title
            actionBar.customView = mCustomView

            val parent = (mCustomView.parent as Toolbar)
            parent.setContentInsetsAbsolute(0,0)

            val params = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
            actionBar.setCustomView(mCustomView, params)
        }
    }

    fun setTitle(title: String) {
        mCustomView.title.text = title
    }
}