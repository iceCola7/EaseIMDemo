package com.cxz.easeimdemo

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.cxz.easeimdemo.im.EaseIMManager

/**
 * @author chenxz
 * @date 2021/6/15
 * @desc
 */
class MyApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        EaseIMManager.instance.initIM(this)

    }

}