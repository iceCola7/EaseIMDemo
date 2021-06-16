package com.cxz.easeimdemo.im.viewholder

import android.view.View
import com.hyphenate.chat.EMCustomMessageBody
import com.hyphenate.chat.EMMessage
import com.hyphenate.easeui.interfaces.MessageListItemClickListener
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder
import com.hyphenate.util.EMLog

class ChatUserCardViewHolder(itemView: View, itemClickListener: MessageListItemClickListener?) :
    EaseChatRowViewHolder(itemView, itemClickListener) {

    override fun onBubbleClick(message: EMMessage) {
        super.onBubbleClick(message)
        if (message.type == EMMessage.Type.CUSTOM) {
            val messageBody = message.body as EMCustomMessageBody
            val event = messageBody.event()
            if (event == "userCard") {
                val params = messageBody.params
                val uId = params["uid"]
                val avatar = params["avatar"]
                val nickname = params["nickname"]

            } else {
                EMLog.e("ChatUserCardViewHolder", "onBubbleClick uId is empty")
            }
        }
    }
}
