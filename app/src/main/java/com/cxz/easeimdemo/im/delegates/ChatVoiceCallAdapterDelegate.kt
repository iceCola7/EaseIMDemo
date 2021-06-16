package com.cxz.easeimdemo.im.delegates

import android.view.View
import android.view.ViewGroup
import com.cxz.easeimdemo.im.viewholder.ChatVoiceCallViewHolder
import com.cxz.easeimdemo.im.views.ChatRowVoiceCall
import com.hyphenate.chat.EMMessage
import com.hyphenate.easecallkit.base.EaseCallType
import com.hyphenate.easecallkit.utils.EaseMsgUtils
import com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
import com.hyphenate.easeui.interfaces.MessageListItemClickListener
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder
import com.hyphenate.easeui.widget.chatrow.EaseChatRow

class ChatVoiceCallAdapterDelegate : EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder>() {

    override fun isForViewType(item: EMMessage, position: Int): Boolean {
        val isRtcCall =
            item.getStringAttribute("msgType", "") == EaseMsgUtils.CALL_MSG_INFO
        val isVoiceCall =
            item.getIntAttribute(EaseMsgUtils.CALL_TYPE, 0) == EaseCallType.SINGLE_VOICE_CALL.code
        return item.type == EMMessage.Type.TXT && isRtcCall && isVoiceCall
    }

    override fun getEaseChatRow(parent: ViewGroup, isSender: Boolean): EaseChatRow {
        return ChatRowVoiceCall(parent.context, isSender)
    }

    override fun createViewHolder(view: View, itemClickListener: MessageListItemClickListener): EaseChatRowViewHolder {
        return ChatVoiceCallViewHolder(view, itemClickListener)
    }
}
