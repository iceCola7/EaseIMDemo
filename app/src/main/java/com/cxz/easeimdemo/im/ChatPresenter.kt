package com.cxz.easeimdemo.im

import com.hyphenate.easeui.manager.EaseChatPresenter

/**
 * @author chenxz
 * @date 2021/6/16
 * @desc 主要用于chat过程中的全局监听，并对相应的事件进行处理
 */
class ChatPresenter : EaseChatPresenter() {

    companion object {
        val instance: ChatPresenter by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            return@lazy ChatPresenter()
        }
    }

}