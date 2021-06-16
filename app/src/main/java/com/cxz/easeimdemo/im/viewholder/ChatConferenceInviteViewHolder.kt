package com.cxz.easeimdemo.im.viewholder

import android.view.View
import com.hyphenate.chat.EMMessage
import com.hyphenate.easeui.interfaces.MessageListItemClickListener
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder

class ChatConferenceInviteViewHolder(itemView: View, itemClickListener: MessageListItemClickListener?) :
    EaseChatRowViewHolder(itemView, itemClickListener) {

    override fun onBubbleClick(message: EMMessage?) {
        super.onBubbleClick(message)
    }

}