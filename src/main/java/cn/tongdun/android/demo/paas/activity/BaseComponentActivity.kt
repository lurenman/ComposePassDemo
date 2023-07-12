package cn.tongdun.android.demo.paas.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * @Author yang.bai.
 * Date: 2023/1/30
 */
abstract class BaseComponentActivity : ComponentActivity(), CoroutineScope by MainScope() {
    protected lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        initModel()
        initView()
    }

    protected open fun initModel() = Unit
    protected abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}