package cn.tongdun.android.demo.paas.util

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import cn.tongdun.android.demo.paas.App
import cn.tongdun.android.demo.paas.Constant
import cn.tongdun.android.demo.paas.R
import cn.tongdun.android.demo.paas.db.Config

/**
 * @Author yang.bai.
 * Date: 2023/2/2
 */

fun generateConfigs(ctx: Context): List<Config> = mutableListOf<Config>().apply {
    add(Config(Constant.PARTNER_CODE, "tongdun"))
    add(Config(Constant.CHANNEL, "tongdun"))
    add(
        Config(
            Constant.CUSTOM_URL,
            ctx.getString(R.string.default_url) + "/android3_8/profile.json"
        )
    )
    add(
        Config(
            Constant.CUSTOM_URL_NEW,
            ctx.getString(R.string.default_url) + "/android3_8/profile.json"
        )
    )
    add(Config(Constant.OVERRIDE_CERTI, false.toString()))
    add(Config(Constant.INSTALLPACKAGES_ENABLE, true.toString()))
    add(Config(Constant.READ_PHONE_ENABLE, true.toString()))
    add(Config(Constant.SENSOR_ENABLE, true.toString()))
    add(Config(Constant.SKIP_GPS, false.toString()))
    add(Config(Constant.TASK_ENABLE, true.toString()))
    add(Config(Constant.USE_DEMOTION, false.toString()))
    add(Config(Constant.WAIT_TIME, "3000"))
    add(Config(Constant.APK_SIGNED_SHA256, ""))
    add(Config(Constant.APPLICATION_ID_SHA256, ""))
    add(Config(Constant.FORCE_TLS_VERSION_ENABLE, false.toString()))
    add(Config(Constant.HTTP_TIME_OUT, "15000"))
    add(Config(Constant.CUSTOM_PATH, ""))
    add(Config(Constant.COLLECT_LEVEL, false.toString()))
    add(Config(Constant.BLACKBOX_MAXSIZE, "1024 * 5"))
}

fun String.showToast() = Toast.makeText(App.context, this, Toast.LENGTH_SHORT).show()

fun String.showLongToast() = Toast.makeText(App.context, this, Toast.LENGTH_LONG).show()

fun Int.showToast() = Toast.makeText(App.context, this, Toast.LENGTH_SHORT).show()

fun Int.showLongToast() = Toast.makeText(App.context, this, Toast.LENGTH_LONG).show()


/**
 * 扩展长按事件
 */
fun Modifier.longClick(onLongClick: (Offset) -> Unit): Modifier =
    pointerInput(this) {
        detectTapGestures(
            onLongPress = onLongClick
        )
    }



