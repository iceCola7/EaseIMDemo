package com.cxz.easeimdemo.im.views

import android.content.Context
import android.view.View
import android.widget.TextView
import com.cxz.easeimdemo.R
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.easeui.widget.chatrow.EaseChatRow

class ChatRowVoiceCall(context: Context?, isSender: Boolean) : EaseChatRow(context, isSender) {

    private var contentView: TextView? = null

    override fun onInflateView() {
        val layoutId =
            if (showSenderType) R.layout.ease_row_sent_voice_call else R.layout.ease_row_received_voice_call
        inflater.inflate(layoutId, this)
    }

    override fun onFindViewById() {
        contentView = findViewById<View>(R.id.tv_chatcontent) as TextView
    }

    override fun onSetUpView() {
        val txtBody = message.body as EMTextMessageBody
        contentView?.text = txtBody.message
    }
}