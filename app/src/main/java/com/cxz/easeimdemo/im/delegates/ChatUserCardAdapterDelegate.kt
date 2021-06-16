package com.cxz.easeimdemo.im.delegates

import android.view.View
import android.view.ViewGroup
import com.cxz.easeimdemo.im.viewholder.ChatUserCardViewHolder
import com.cxz.easeimdemo.im.views.ChatRowUserCard
import com.hyphenate.chat.EMCustomMessageBody
import com.hyphenate.chat.EMMessage
import com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
import com.hyphenate.easeui.interfaces.MessageListItemClickListener
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder
import com.hyphenate.easeui.widget.chatrow.EaseChatRow

class ChatUserCardAdapterDelegate : EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder>() {

    override fun isForViewType(item: EMMessage, position: Int): Boolean {
        if (item.type == EMMessage.Type.CUSTOM) {
            val messageBody = item.body as EMCustomMessageBody
            val event = messageBody.event()
            return event == "userCard"
        }
        return false
    }

    override fun getEaseChatRow(parent: ViewGroup, isSender: Boolean): EaseChatRow {
        return ChatRowUserCard(parent.context, isSender)
    }

    override fun createViewHolder(view: View, itemClickListener: MessageListItemClickListener): EaseChatRowViewHolder {
        return ChatUserCardViewHolder(view, itemClickListener)
    }
}

