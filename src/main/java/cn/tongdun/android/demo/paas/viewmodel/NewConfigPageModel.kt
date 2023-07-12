package cn.tongdun.android.demo.paas.viewmodel

import android.text.TextUtils
import cn.tongdun.android.demo.paas.Constant
import cn.tongdun.android.demo.paas.db.Config
import cn.tongdun.android.demo.paas.util.showToast
import cn.tongdun.android.shell.FMAgent

/**
 * @Author yang.bai.
 * Date: 2023/2/2
 */
class NewConfigPageModel : DataModel() {
    /**
     * 读取数据库中的配置
     */
    fun getOptionConfigBuilder(it: List<Config>): FMAgent.Builder {
        val customUrl = it.find { config ->
            config.key == Constant.CUSTOM_URL_NEW
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
        val builder = FMAgent.Builder()
        return builder.apply {
            if (!TextUtils.isEmpty(partnerCode)) {
                partner(partnerCode)
            }
            if (!TextUtils.isEmpty(customUrl))
                url(customUrl)
            tdChannel(channel)
            if (!installedAppsEnable)
                disableInstallPackageList()
            if (!readPhoneEnable)
                disableReadPhone()
            if (!sensorEnable)
                disableSensor()
            if (!gpsEnable)
                disableGPS()
            if (!runningTaskEnable)
                disableRunningTasks()
            if (useDemotionEnable)
                useDemotionData()
            waitTime(wait_time)
            if (!TextUtils.isEmpty(apkSignedSHA256)) {
                apkSignedSHA256(apkSignedSHA256)
            }
            if (!TextUtils.isEmpty(applicationIdSHA256)) {
                applicationIdSHA256(applicationIdSHA256)
            }
            if (sslEnable)
                forceTLSVersion()
            httpTimeout(http_timeout)
            if (TextUtils.isEmpty(file_path)) {
                customFilePath(file_path)
            }
            if (collectLevel_m)
                collectLevel(FMAgent.COLLECT_LEVEL_M)
            blackBoxMaxSize(blackbox_MaxSize)
        }
    }

    fun checkObfuscate() {
        if (try {
                Class.forName("cn.tongdun.android.core.FMCore")
                true
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
                false
            }
        ) {
            "sdk没有混淆".showToast()
        } else {
            "sdk已经混淆".showToast()
        }
    }
}