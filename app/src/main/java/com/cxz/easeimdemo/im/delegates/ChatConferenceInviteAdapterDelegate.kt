package com.cxz.easeimdemo.im.delegates

import android.view.View
import android.view.ViewGroup
import com.cxz.easeimdemo.im.viewholder.ChatConferenceInviteViewHolder
import com.cxz.easeimdemo.im.views.ChatRowConferenceInvite
import com.hyphenate.chat.EMMessage
import com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
import com.hyphenate.easeui.interfaces.MessageListItemClickListener
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder
import com.hyphenate.easeui.widget.chatrow.EaseChatRow

class ChatConferenceInviteAdapterDelegate : EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder>() {

    override fun isForViewType(item: EMMessage, position: Int): Boolean {
        return item.type == EMMessage.Type.TXT && item.getStringAttribute("conferenceId", "") != ""
    }

    override fun getEaseChatRow(parent: ViewGroup, isSender: Boolean): EaseChatRow {
        return ChatRowConferenceInvite(parent.context, isSender)
    }

    override fun createViewHolder(
        view: View,
        itemClickListener: MessageListItemClickListener?
    ): EaseChatRowViewHolder {
        return ChatConferenceInviteViewHolder(view, itemClickListener)
    }
}