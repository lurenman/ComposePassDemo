package cn.tongdun.android.demo.paas

import cn.tongdun.android.demo.paas.Constant.APK_SIGNED_SHA256
import cn.tongdun.android.demo.paas.Constant.APPLICATION_ID_SHA256
import cn.tongdun.android.demo.paas.Constant.BLACKBOX_MAXSIZE
import cn.tongdun.android.demo.paas.Constant.CHANNEL
import cn.tongdun.android.demo.paas.Constant.COLLECT_LEVEL
import cn.tongdun.android.demo.paas.Constant.CUSTOM_PATH
import cn.tongdun.android.demo.paas.Constant.FORCE_TLS_VERSION_ENABLE
import cn.tongdun.android.demo.paas.Constant.HTTP_TIME_OUT
import cn.tongdun.android.demo.paas.Constant.INSTALLPACKAGES_ENABLE
import cn.tongdun.android.demo.paas.Constant.OVERRIDE_CERTI
import cn.tongdun.android.demo.paas.Constant.PARTNER_CODE
import cn.tongdun.android.demo.paas.Constant.READ_PHONE_ENABLE
import cn.tongdun.android.demo.paas.Constant.SENSOR_ENABLE
import cn.tongdun.android.demo.paas.Constant.SKIP_GPS
import cn.tongdun.android.demo.paas.Constant.TASK_ENABLE
import cn.tongdun.android.demo.paas.Constant.USE_DEMOTION
import cn.tongdun.android.demo.paas.Constant.WAIT_TIME
import cn.tongdun.android.demo.paas.db.Config

/**
 * @Author yang.bai.
 * Date: 2023/1/31
 * Drawer adapter实体装饰config
 */
class ConfigItem(config: Config) : Comparator<ConfigItem.ViewType> {
    val data: Config = config
    var viewType: ViewType = ViewType.UNDEFINED
    var dataType: DataType = DataType.UNDEFINED
    var uitext = ""

    init {
        data.run {
            parseType(this)
            parseUiText(this)
        }
    }

    /**
     * 解析config数据，设置view/data类型
     */
    private fun parseType(config: Config) {
        when (config.key) {
            PARTNER_CODE, CHANNEL, APK_SIGNED_SHA256, APPLICATION_ID_SHA256, CUSTOM_PATH -> {
                viewType = ViewType.SET
                dataType = DataType.STRING
            }
            WAIT_TIME, HTTP_TIME_OUT, BLACKBOX_MAXSIZE -> {
                viewType = ViewType.SET
                dataType = DataType.INT
            }
            OVERRIDE_CERTI, INSTALLPACKAGES_ENABLE, READ_PHONE_ENABLE, SENSOR_ENABLE,
            SKIP_GPS, TASK_ENABLE, USE_DEMOTION, FORCE_TLS_VERSION_ENABLE, COLLECT_LEVEL -> {
                viewType = ViewType.SWITCH
                dataType = DataType.BOOLEAN
            }
            else -> {
                viewType = ViewType.UNDEFINED
                dataType = DataType.UNDEFINED
            }
        }
    }

    private fun parseUiText(config: Config) {
        uitext = when (config.key) {
            PARTNER_CODE -> {
                OptionText.PARTNER_CODE
            }
            CHANNEL -> {
                OptionText.TD_CHANNEL
            }
            APK_SIGNED_SHA256 -> OptionText.APK_SIGNED_SHA256
            APPLICATION_ID_SHA256 -> OptionText.APK_APPLICATION_ID
            CUSTOM_PATH -> OptionText.FILE_PATH
            WAIT_TIME -> OptionText.WAIT_TIME
            HTTP_TIME_OUT -> OptionText.HTTP_TIMEOUT
            BLACKBOX_MAXSIZE -> OptionText.BLACKBOX_MAXSIZE
            OVERRIDE_CERTI -> OptionText.OVERRIDE_CERTI
            INSTALLPACKAGES_ENABLE -> OptionText.INSTALLED_APP_LIST
            READ_PHONE_ENABLE -> OptionText.READ_PHONE
            SENSOR_ENABLE -> OptionText.SENSOR
            SKIP_GPS -> OptionText.GPS
            TASK_ENABLE -> OptionText.RUNNING_TASK
            USE_DEMOTION -> OptionText.USE_DEMOTION
            FORCE_TLS_VERSION_ENABLE -> OptionText.SSL_ENABLE
            COLLECT_LEVEL -> OptionText.COLLECT_LEVEL
            else -> {
                ""
            }
        }
    }

    object OptionText {
        const val CLEAN_XXID = "清空xxid"
        const val INSTALLED_APP_LIST = "读取应用列表"
        const val GPS = "gps信息采集"
        const val READ_PHONE = "获取手机信息"
        const val RUNNING_TASK = "采集运行中的任务"
        const val USE_DEMOTION = "使用降级"
        const val SENSOR = "传感器采集"
        const val PARTNER_CODE = "partner"
        const val TD_CHANNEL = "渠道"
        const val FILE_PATH = "设置目录"
        const val WAIT_TIME = "等待时间"
        const val HTTP_TIMEOUT = "请求超时时间"
        const val APK_SIGNED_SHA256 = "partner+apk签名SHA256"
        const val APK_APPLICATION_ID = "partner+apk包名SHA256"
        const val SSL_ENABLE = "强制SSLv1.1"
        const val COLLECT_LEVEL = "降级水平M"
        const val BLACKBOX_MAXSIZE = "blackbox最大size"
        const val OVERRIDE_CERTI = "忽略证书"
    }

    sealed class ViewType(val value: Int) {
        object UNDEFINED : ViewType(-1)
        object SWITCH : ViewType(0)
        object SET : ViewType(1)
    }

    sealed class DataType {
        object UNDEFINED : DataType()
        object INT : DataType()
        object BOOLEAN : DataType()
        object STRING : DataType()
    }

    override fun compare(o1: ViewType, o2: ViewType): Int {
        return o1.value - o2.value
    }
}