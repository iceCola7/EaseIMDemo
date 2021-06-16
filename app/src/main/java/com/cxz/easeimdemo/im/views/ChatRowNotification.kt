package com.cxz.easeimdemo.im.views

import android.content.Context
import android.widget.TextView
import com.cxz.easeimdemo.R
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.easeui.widget.chatrow.EaseChatRow

class ChatRowNotification(context: Context?, isSender: Boolean) : EaseChatRow(context, isSender) {

    private var contentView: TextView? = null

    override fun onInflateView() {
        inflater.inflate(R.layout.demo_row_notification, this)
    }

    override fun onFindViewById() {
        contentView = findViewById(R.id.tv_chatcontent)
    }

    override fun onSetUpView() {
        val txtBody = message.body as EMTextMessageBody
        contentView?.text = txtBody.message
    }
}