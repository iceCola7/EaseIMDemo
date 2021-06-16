package com.cxz.easeimdemo.im.delegates

import android.view.View
import android.view.ViewGroup
import com.cxz.easeimdemo.im.viewholder.ChatRecallViewHolder
import com.cxz.easeimdemo.im.views.ChatRowRecall
import com.hyphenate.chat.EMMessage
import com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
import com.hyphenate.easeui.interfaces.MessageListItemClickListener
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder
import com.hyphenate.easeui.widget.chatrow.EaseChatRow

class ChatRecallAdapterDelegate : EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder>() {

    override fun isForViewType(item: EMMessage, position: Int): Boolean {
        return item.type == EMMessage.Type.TXT && item.getBooleanAttribute("message_recall", false)
    }

    override fun getEaseChatRow(parent: ViewGroup, isSender: Boolean): EaseChatRow {
        return ChatRowRecall(parent.context, isSender)
    }

    override fun createViewHolder(view: View, itemClickListener: MessageListItemClickListener): EaseChatRowViewHolder {
        return ChatRecallViewHolder(view, itemClickListener)
    }
}