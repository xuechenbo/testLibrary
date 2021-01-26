package com.monebac.com

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.monebac.com.utils.isMainProcess

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        // 主进程初始化
        if (isMainProcess(this)) {
            init()
        }
    }

    private fun init() {


    }


}