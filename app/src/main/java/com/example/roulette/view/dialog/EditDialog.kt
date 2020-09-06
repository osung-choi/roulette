package com.example.roulette.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.example.roulette.R
import com.example.roulette.repository.database.RouletteDatabase
import kotlinx.android.synthetic.main.dialog_edit.*


class EditDialog: Dialog {
    private val listener: (String) -> Unit
    private var topText = ""
    private var yesText = ""
    private var noText = ""
    constructor(context: Context, listener: (String) -> Unit) : super(context) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit)

        setCanceledOnTouchOutside(false)

        title.text = topText
        dialogAdd.text = yesText
        dialogCancel.text = noText

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

    fun setTitle(topTitle: String): EditDialog {
        topText = topTitle
        return this
    }

    fun setYesContent(yes: String): EditDialog {
        yesText = yes
        return this
    }

    fun setNoContent(no: String): EditDialog {
        noText = no
        return this
    }

    override fun dismiss() {
        super.dismiss()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}