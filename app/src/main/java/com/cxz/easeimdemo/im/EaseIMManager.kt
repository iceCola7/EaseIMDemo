package com.cxz.easeimdemo.im

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.cxz.easeimdemo.im.delegates.*
import com.hyphenate.EMCallBack
import com.hyphenate.EMConnectionListener
import com.hyphenate.EMError
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMOptions
import com.hyphenate.easecallkit.EaseCallKit
import com.hyphenate.easecallkit.EaseCallKit.EaseCallError
import com.hyphenate.easecallkit.base.*
import com.hyphenate.easeui.EaseIM
import com.hyphenate.easeui.delegate.*
import com.hyphenate.easeui.domain.EaseAvatarOptions
import com.hyphenate.easeui.domain.EaseEmojicon
import com.hyphenate.easeui.domain.EaseUser
import com.hyphenate.easeui.manager.EaseMessageTypeSetManager
import com.hyphenate.easeui.provider.EaseEmojiconInfoProvider
import com.hyphenate.easeui.provider.EaseSettingsProvider
import com.hyphenate.push.EMPushConfig
import com.hyphenate.push.EMPushHelper
import com.hyphenate.push.EMPushType
import com.hyphenate.push.PushListener
import com.hyphenate.util.EMLog
import org.json.JSONObject
import java.util.*

/**
 * @author chenxz
 * @date 2021/6/16
 * @desc IM 管理类
 */
class EaseIMManager {

    companion object {
        val instance: EaseIMManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            return@lazy EaseIMManager()
        }
    }

    private lateinit var context: Context

    /**
     * 初始化
     */
    fun initIM(application: Application) {
        this.context = application.applicationContext

        val options = EMOptions().apply {
            autoLogin = true
            // 默认添加好友时，是不需要验证的，改成需要验证
            acceptInvitationAlways = false
            // 是否需要接受已读确认
            requireAck = true
            // 设置是否需要接受方送达确认,默认false
            requireDeliveryAck = false
            useRtcConfig = true
            // 设置是否允许聊天室owner离开并删除会话记录，意味着owner再不会受到任何消息
            allowChatroomOwnerLeave(true)
            // 设置退出(主动和被动退出)群组时是否删除聊天消息
            isDeleteMessagesAsExitGroup = true
            // 设置是否自动接受加群邀请
            isAutoAcceptGroupInvitation = true
            // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
            autoTransferMessageAttachments = true
            // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
            setAutoDownloadThumbnail(true)

            // 配置第三方推送服务
            val builder = EMPushConfig.Builder(context)
            builder.enableVivoPush() // 需要在AndroidManifest.xml中配置appId和appKey
                .enableMeiZuPush("", "")
                .enableMiPush("", "")
                .enableOppoPush("", "")
                .enableHWPush() // 需要在AndroidManifest.xml中配置appId
                .enableFCM("")
            pushConfig = builder.build()
        }

        // 初始化
        EMClient.getInstance().init(application, options)
        // 在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true)

        // set Call options
        setCallOptions()
        // 初始化Push
        initPush(context)
        // 初始化ease ui相关
        initEaseUI(context)
        //注册对话类型
        registerConversationType()
        // callKit初始化
        initCallKit(context)
        // 初始化连接状态变化监听
        initConnectionListener()
    }

    private fun setCallOptions() {
        val headsetReceiver = HeadsetReceiver()
        val headsetFilter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
        context.registerReceiver(headsetReceiver, headsetFilter)
    }

    /**
     * 初始化Push
     */
    private fun initPush(context: Context) {
        if (EaseIM.getInstance().isMainProcess(context)) {
            // OPPO SDK升级到2.1.0后需要进行初始化
            // HeytapPushManager.init(context, true)
            EMPushHelper.getInstance().setPushListener(object : PushListener() {
                override fun onError(pushType: EMPushType, errorCode: Long) {
                    // TODO: 返回的errorCode仅9xx为环信内部错误，可从EMError中查询，其他错误请根据pushType去相应第三方推送网站查询。
                    EMLog.e("PushClient", "Push client occur a error: $pushType - $errorCode")
                }
            })
        }
    }

    /**
     * ChatPresenter中添加了网络连接状态监听，多端登录监听，群组监听，联系人监听，聊天室监听
     */
    private fun initEaseUI(context: Context) {
        //添加ChatPresenter,ChatPresenter中添加了网络连接状态监听，
        EaseIM.getInstance().addChatPresenter(ChatPresenter.instance)
        EaseIM.getInstance().setSettingsProvider(object : EaseSettingsProvider {
            override fun isMsgNotifyAllowed(message: EMMessage?): Boolean {
                return false
            }

            override fun isMsgSoundAllowed(message: EMMessage?): Boolean {
                return false
            }

            override fun isMsgVibrateAllowed(message: EMMessage?): Boolean {
                return false
            }

            override fun isSpeakerOpened(): Boolean {
                return false
            }
        })
            .setEmojiconInfoProvider(object : EaseEmojiconInfoProvider {
                override fun getEmojiconInfo(emojiconIdentityCode: String?): EaseEmojicon? {
                    return null
                }

                override fun getTextEmojiconMapping(): MutableMap<String, Any>? {
                    return null
                }
            })
            .setAvatarOptions(EaseAvatarOptions().apply {
                // 统一配置头像
                avatarShape = 1
            })
            .setUserProvider { username ->
                // To get instance of EaseUser, here we get it from the user list in memory
                // You'd better cache it if you get it from your server
                var user: EaseUser? = null
                user
            }
    }

    /**
     * 注册对话类型
     */
    private fun registerConversationType() {
        EaseMessageTypeSetManager.getInstance()
            .addMessageType(EaseExpressionAdapterDelegate::class.java) //自定义表情
            .addMessageType(EaseFileAdapterDelegate::class.java) //文件
            .addMessageType(EaseImageAdapterDelegate::class.java) //图片
            .addMessageType(EaseLocationAdapterDelegate::class.java) //定位
            .addMessageType(EaseVideoAdapterDelegate::class.java) //视频
            .addMessageType(EaseVoiceAdapterDelegate::class.java) //声音
            .addMessageType(ChatConferenceInviteAdapterDelegate::class.java) //语音邀请
            .addMessageType(ChatRecallAdapterDelegate::class.java) //消息撤回
            .addMessageType(ChatVideoCallAdapterDelegate::class.java) //视频通话
            .addMessageType(ChatVoiceCallAdapterDelegate::class.java) //语音通话
            .addMessageType(ChatUserCardAdapterDelegate::class.java) //名片消息
            .addMessageType(EaseCustomAdapterDelegate::class.java) //自定义消息
            .addMessageType(ChatNotificationAdapterDelegate::class.java) //入群等通知消息
            .setDefaultMessageType(EaseTextAdapterDelegate::class.java) //文本
    }

    /**
     * 初始化 Call Kit
     */
    private fun initCallKit(context: Context) {
        val callKitConfig = EaseCallKitConfig().apply {
            // 设置呼叫超时时间
            callTimeOut = 30 * 1000L
            // 设置声网AgoraAppId
            agoraAppId = "15cb0d28b87b425ea613fc46f7c9f974"
            isEnableRTCToken = true
        }
        EaseCallKit.getInstance().init(context, callKitConfig)
        // 增加EaseCallkit监听
        EaseCallKit.getInstance().setCallKitListener(object : EaseCallKitListener {
            override fun onInviteUsers(context: Context?, users: Array<out String>?, ext: JSONObject?) {
            }

            override fun onEndCallWithReason(
                callType: EaseCallType?,
                channelName: String?,
                reason: EaseCallEndReason?,
                callTime: Long
            ) {
            }

            override fun onReceivedCall(callType: EaseCallType?, userId: String?, ext: JSONObject?) {
            }

            override fun onCallError(type: EaseCallError?, errorCode: Int, description: String?) {
            }

            override fun onInViteCallMessageSent() {
            }

            override fun onRemoteUserJoinChannel(
                channelName: String?,
                userName: String?,
                uid: Int,
                callback: EaseGetUserAccountCallback?
            ) {
            }
        })
    }


    /**
     * 初始化连接状态变化监听
     */
    private fun initConnectionListener() {
        EMClient.getInstance().addConnectionListener(object : EMConnectionListener {
            override fun onConnected() {
            }

            override fun onDisconnected(errorCode: Int) {
                when (errorCode) {
                    EMError.USER_REMOVED -> {
                    }
                    EMError.USER_LOGIN_ANOTHER_DEVICE -> {
                        // 同一个账号异地登录
                    }
                    EMError.SERVER_SERVICE_RESTRICTED -> {
                    }
                    EMError.USER_KICKED_BY_CHANGE_PASSWORD -> {
                    }
                    EMError.USER_KICKED_BY_OTHER_DEVICE -> {
                    }
                }
            }
        })
    }

    /**
     * logout
     * @param unbindDeviceToken Boolean whether you need unbind your device token
     * @param callback EMCallBack callback
     */
    fun logout(unbindDeviceToken: Boolean, callback: EMCallBack? = null) {
        EMClient.getInstance().logout(unbindDeviceToken, object : EMCallBack {
            override fun onSuccess() {
                callback?.onSuccess()
            }

            override fun onError(code: Int, error: String?) {
                callback?.onError(code, error)
            }

            override fun onProgress(progress: Int, status: String?) {
                callback?.onProgress(progress, status)
            }
        })
    }
}