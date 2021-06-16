package com.cxz.easeimdemo.im.viewholder

import android.view.View
import com.hyphenate.chat.EMMessage
import com.hyphenate.easecallkit.EaseCallKit
import com.hyphenate.easecallkit.base.EaseCallType
import com.hyphenate.easeui.interfaces.MessageListItemClickListener
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder

class ChatVideoCallViewHolder(itemView: View, itemClickListener: MessageListItemClickListener?) :
    EaseChatRowViewHolder(itemView, itemClickListener) {

    override fun onBubbleClick(message: EMMessage) {
        super.onBubbleClick(message)
        if (message.direct() == EMMessage.Direct.SEND) {
            EaseCallKit.getInstance().startSingleCall(EaseCallType.SINGLE_VIDEO_CALL, message.to, null)
        } else {
            EaseCallKit.getInstance().startSingleCall(EaseCallType.SINGLE_VIDEO_CALL, message.from, null)
        }
    }
}
