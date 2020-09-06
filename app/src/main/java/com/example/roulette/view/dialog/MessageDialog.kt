package com.example.roulette.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.roulette.R
import kotlinx.android.synthetic.main.dialog_edit.*

import kotlinx.android.synthetic.main.dialog_message.*
import kotlinx.android.synthetic.main.dialog_message.dialogAdd
import kotlinx.android.synthetic.main.dialog_message.dialogCancel
import kotlinx.android.synthetic.main.dialog_message.title

class MessageDialog: Dialog {
    private val listener: (Boolean) -> Unit
    private var topText = ""
    private var messageText = ""
    private var yesText = ""
    private var noText = ""

    constructor(context: Context, listener: (Boolean) -> Unit): super(context) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_message)

        setCanceledOnTouchOutside(false)

        title.text = topText
        dialogAdd.text = yesText
        dialogCancel.text = noText
        message.text = messageText

        dialogCancel.setOnClickListener {
            listener.invoke(false)
            dismiss()
        }

        dialogAdd.setOnClickListener {
            listener.invoke(true)
            dismiss()
        }
    }

    fun setTitle(topTitle: String): MessageDialog {
        topText = topTitle
        return this
    }

    fun setMessage(content: String): MessageDialog {
        messageText = content
        return this
    }

    fun setYesContent(yes: String): MessageDialog {
        yesText = yes
        return this
    }

    fun setNoContent(no: String): MessageDialog {
        noText = no
        return this
    }

}