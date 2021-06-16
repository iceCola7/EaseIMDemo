package com.cxz.easeimdemo.im.delegates

import android.view.View
import android.view.ViewGroup
import com.cxz.easeimdemo.im.viewholder.ChatNotificationViewHolder
import com.cxz.easeimdemo.im.views.ChatRowNotification
import com.hyphenate.chat.EMMessage
import com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
import com.hyphenate.easeui.interfaces.MessageListItemClickListener
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder
import com.hyphenate.easeui.widget.chatrow.EaseChatRow

class ChatNotificationAdapterDelegate : EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder>() {

    override fun isForViewType(item: EMMessage, position: Int): Boolean {
        return item.type == EMMessage.Type.TXT && item.getBooleanAttribute("em_notification_type", false)
    }

    override fun getEaseChatRow(parent: ViewGroup, isSender: Boolean): EaseChatRow {
        return ChatRowNotification(parent.context, isSender)
    }

    override fun createViewHolder(view: View, itemClickListener: MessageListItemClickListener): EaseChatRowViewHolder {
        return ChatNotificationViewHolder(view, itemClickListener)
    }
}
