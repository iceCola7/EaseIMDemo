package com.cxz.easeimdemo.im.views

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.cxz.easeimdemo.R
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.easeui.widget.chatrow.EaseChatRow


class ChatRowConferenceInvite(context: Context?, isSender: Boolean) : EaseChatRow(context, isSender) {

    private var contentView: TextView? = null

    override fun onInflateView() {
        val layoutId =
            if (showSenderType) R.layout.demo_row_sent_conference_invite else R.layout.demo_row_received_conference_invite
        inflater.inflate(layoutId, this)
    }

    override fun onFindViewById() {
        contentView = findViewById<View>(R.id.tv_chatcontent) as TextView
    }

    override fun onSetUpView() {
        val txtBody = message.body as EMTextMessageBody
        var message = txtBody.message
        if (!TextUtils.isEmpty(message) && message.contains("-")) {
            message = """
                ${message.substring(0, message.indexOf("-") + 1)}
                ${message.substring(message.indexOf("-") + 1)}
                """.trimIndent()
        }
        contentView?.text = message
    }
}