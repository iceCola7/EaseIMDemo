package com.cxz.easeimdemo.im.delegates

import android.view.View
import android.view.ViewGroup
import com.cxz.easeimdemo.im.viewholder.ChatVideoCallViewHolder
import com.cxz.easeimdemo.im.views.ChatRowVideoCall
import com.hyphenate.chat.EMMessage
import com.hyphenate.easecallkit.base.EaseCallType
import com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
import com.hyphenate.easeui.interfaces.MessageListItemClickListener
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder
import com.hyphenate.easeui.widget.chatrow.EaseChatRow

class ChatVideoCallAdapterDelegate : EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder>() {

    override fun isForViewType(item: EMMessage, position: Int): Boolean {
        val isRtcCall = item.getStringAttribute("msgType", "") == "rtcCallWithAgora"
        val isVideoCall = item.getIntAttribute("msgType", 0) == EaseCallType.SINGLE_VIDEO_CALL.code
        return item.type == EMMessage.Type.TXT && isRtcCall && isVideoCall
    }

    override fun getEaseChatRow(parent: ViewGroup, isSender: Boolean): EaseChatRow {
        return ChatRowVideoCall(parent.context, isSender)
    }

    override fun createViewHolder(view: View, itemClickListener: MessageListItemClickListener): EaseChatRowViewHolder {
        return ChatVideoCallViewHolder(view, itemClickListener)
    }
}