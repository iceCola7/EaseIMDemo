package com.cxz.easeimdemo.im.views

import android.content.Context
import android.view.View
import android.widget.TextView
import com.cxz.easeimdemo.R
import com.hyphenate.chat.EMMessage
import com.hyphenate.easeui.widget.chatrow.EaseChatRow

class ChatRowRecall(context: Context?, isSender: Boolean) : EaseChatRow(context, isSender) {

    private var contentView: TextView? = null

    override fun onInflateView() {
        inflater.inflate(R.layout.demo_row_recall_message, this)
    }

    override fun onFindViewById() {
        contentView = findViewById<View>(R.id.text_content) as TextView
    }

    override fun onSetUpView() {
        // 设置显示内容
        var messageStr: String? = null
        messageStr = if (message.direct() == EMMessage.Direct.SEND) {
            String.format(context.getString(R.string.msg_recall_by_self))
        } else {
            String.format(context.getString(R.string.msg_recall_by_user), message.from)
        }
        contentView!!.text = messageStr
    }
}
