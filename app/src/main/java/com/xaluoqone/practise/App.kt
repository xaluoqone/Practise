package com.xaluoqone.practise

import android.app.Application
import com.xaluoqone.practise.ktx.loge

class App : Application() {
    companion object {
        lateinit var instance: App
        const val isDebug = true
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val defExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            loge("${t.name}发现崩溃异常：${e.stackTraceToString()}")
            defExceptionHandler?.uncaughtException(t, e)
        }
    }
}