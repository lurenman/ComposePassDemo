package cn.tongdun.android.demo.paas

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import cn.tongdun.android.demo.paas.db.ConfigDb

/**
 * @Author yang.bai.
 * Date: 2023/1/31
 */
class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: App

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var db: ConfigDb
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        db = ConfigDb.getInstance(context)
        instance = this
    }
}