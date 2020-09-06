package com.example.roulette.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.example.roulette.R
import kotlinx.android.synthetic.main.dialog_add_item.*


class AddItemDialog: Dialog {
    private val listener: (String) -> Unit

    constructor(context: Context, listener: (String) -> Unit) : super(context) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_item)

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

        dialogCancel.setOnClickListener {
            dismiss()
        }

        dialogAdd.setOnClickListener {
            if(!itemName.text.toString().isNullOrEmpty()) {
                listener.invoke(itemName.text.toString())

                dismiss()
            }
        }
    }

    override fun dismiss() {
        super.dismiss()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}