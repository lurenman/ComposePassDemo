package cn.tongdun.android.demo.paas.viewmodel

import android.text.TextUtils
import cn.tongdun.android.demo.paas.Constant
import cn.tongdun.android.demo.paas.db.Config
import cn.tongdun.android.shell.FMAgent

/**
 * @Author yang.bai.
 * Date: 2023/2/2
 */
class OldConfigPageModel : DataModel() {

    /**
     * 读取数据库中的配置
     */
    fun getOptionConfigMap(it: List<Config>): MutableMap<String, Any?> {
        val customUrl = it.find { config ->
            config.key == Constant.CUSTOM_URL
        }!!.value
        val installedAppsEnable = it.find { config ->
            config.key == Constant.INSTALLPACKAGES_ENABLE
        }!!.value.toBoolean()

        val gpsEnable = !it.find { config ->
            config.key == Constant.SKIP_GPS
        }!!.value.toBoolean()

        val readPhoneEnable = it.find { config ->
            config.key == Constant.READ_PHONE_ENABLE
        }!!.value.toBoolean()

        val runningTaskEnable = it.find { config ->
            config.key == Constant.TASK_ENABLE
        }!!.value.toBoolean()

        val useDemotionEnable = it.find { config ->
            config.key == Constant.USE_DEMOTION
        }!!.value.toBoolean()

        val sensorEnable = it.find { config ->
            config.key == Constant.SENSOR_ENABLE
        }!!.value.toBoolean()

        val sslEnable = it.find { config ->
            config.key == Constant.FORCE_TLS_VERSION_ENABLE
        }!!.value.toBoolean()

        val collectLevel_m = it.find { config ->
            config.key == Constant.COLLECT_LEVEL
        }!!.value.toBoolean()

        val file_path = it.find { config ->
            config.key == Constant.CUSTOM_PATH
        }!!.value

        val partnerCode = it.find { config ->
            config.key == Constant.PARTNER_CODE
        }!!.value
        val channel = it.find { config ->
            config.key == Constant.CHANNEL
        }!!.value
        val waitTime = it.find { config ->
            config.key == Constant.WAIT_TIME
        }!!.value
        val httpTimeout = it.find { config ->
            config.key == Constant.HTTP_TIME_OUT
        }!!.value
        val apkSignedSHA256 = it.find { config ->
            config.key == Constant.APK_SIGNED_SHA256
        }!!.value
        val applicationIdSHA256 = it.find { config ->
            config.key == Constant.APPLICATION_ID_SHA256
        }!!.value
        val blackboxMaxSize = it.find { config ->
            config.key == Constant.BLACKBOX_MAXSIZE
        }!!.value
        var wait_time = 3000
        var http_timeout = 60000
        var blackbox_MaxSize = 1024 * 5
        try {
            wait_time = waitTime.toInt()
            http_timeout = httpTimeout.toInt()
            blackbox_MaxSize = blackboxMaxSize.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        val option: MutableMap<String, Any?> = HashMap()
        option.apply {
            if (!TextUtils.isEmpty(partnerCode)) {
                option[FMAgent.OPTION_PARTNER_CODE] = partnerCode
            }
            option[FMAgent.OPTION_CUSTOM_URL] = customUrl
            option[FMAgent.OPTION_CHANNEL] = channel
            option[FMAgent.OPTION_INSTALLPACKAGES_ENABLE] = installedAppsEnable
            option[FMAgent.OPTION_READ_PHONE_ENABLE] = readPhoneEnable
            option[FMAgent.OPTION_SENSOR_ENABLE] = sensorEnable
            option[FMAgent.OPTION_SKIP_GPS] = !gpsEnable
            option[FMAgent.OPTION_TASK_ENABLE] = runningTaskEnable
            option[FMAgent.OPTION_USE_DEMOTION] = useDemotionEnable
            option[FMAgent.OPTION_WAIT_TIME] = wait_time
            if (!TextUtils.isEmpty(apkSignedSHA256)) {
                option[FMAgent.OPTION_APK_SIGNED_SHA256] = apkSignedSHA256
            }
            if (!TextUtils.isEmpty(applicationIdSHA256)) {
                option[FMAgent.OPTION_APPLICATION_ID_SHA256] = applicationIdSHA256
            }
            option[FMAgent.OPTION_FORCE_TLS_VERSION_ENABLE] = sslEnable
            option[FMAgent.OPTION_HTTP_TIME_OUT] = http_timeout
            if (TextUtils.isEmpty(file_path)) {
                option[FMAgent.OPTION_CUSTOM_PATH] = file_path
            }
            if (collectLevel_m) option[FMAgent.OPTION_COLLECT_LEVEL] = FMAgent.COLLECT_LEVEL_M
            option[FMAgent.OPTION_BLACKBOX_MAXSIZE] = blackbox_MaxSize
        }
        return option
    }
}