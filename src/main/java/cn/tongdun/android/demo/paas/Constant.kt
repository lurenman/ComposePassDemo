package cn.tongdun.android.demo.paas

/**
 * @Author yang.bai.
 * Date: 2023/1/30
 */
object Constant {
    var DEBUG = true

    //客户标识
    const val PARTNER_CODE = "OPTION_PARTNER_CODE"

    //请求配置请求接口传参
    const val CHANNEL = "OPTION_CHANNEL"

    //自定义url 企业级专用
    const val CUSTOM_URL = "CUSTOM_URL"

    //自定义url 企业级专用
    const val CUSTOM_URL_NEW = "CUSTOM_URL_NEW"

    //是否进行域名校验
    const val OVERRIDE_CERTI = "OPTION_OVERRIDE_CERTI"

    //是否采集安装包
    const val INSTALLPACKAGES_ENABLE = "OPTION_INSTALLPACKAGES_ENABLE"

    //是否允许获取手机信息,true允许获取（默认），false不允许
    const val READ_PHONE_ENABLE = "OPTION_READ_PHONE_ENABLE"

    //是否采集传感器
    const val SENSOR_ENABLE = "OPTION_SENSOR_ENABLE"

    //忽略GPS字段(不跳出授权提示)
    const val SKIP_GPS = "OPTION_SKIP_GPS"

    //是否允许获取正在运行的任务
    const val TASK_ENABLE = "OPTION_TASK_ENABLE"

    //是否允许获取正在运行的任务
    const val USE_DEMOTION = "OPTION_USE_DEMOTION"

    //允许等待时间
    const val WAIT_TIME = "OPTION_WAIT_TIME"

    //设置应用签名SHA256
    const val APK_SIGNED_SHA256 = "OPTION_APK_SIGNED_SHA256"

    //设置应用包名
    const val APPLICATION_ID_SHA256 = "OPTION_APPLICATION_ID_SHA256"

    //是否强制使用TSL-v1.1，true强制TLS版本，false不强制，默认false
    const val FORCE_TLS_VERSION_ENABLE = "OPTION_FORCE_TLS_VERSION_ENABLE"

    //设置网络请求超时（ms）
    const val HTTP_TIME_OUT = "OPTION_HTTP_TIME_OUT"

    //自定义client_id外部存储路径（弃用）
    const val CUSTOM_PATH = "OPTION_CUSTOM_PATH"

    //采集字段等级，只针对降级数据。默认L，M：降级删除字段较多，H：预留
    const val COLLECT_LEVEL = "OPTION_COLLECT_LEVEL"

    //blackbox 最大长度
    const val BLACKBOX_MAXSIZE = "OPTION_BLACKBOX_MAXSIZE"


    const val PARAMS_BLACK_BOX = "black_box"
    const val PARAMS_RISK_SERVICE = "risk_service"
    const val PARAMS_PARTNER_CODE = "partner_code"
    const val PARAMS_EVENT_ID = "event_id"
    const val PARAMS_RESP_DETAIL_TYPE = "resp_detail_type"
}