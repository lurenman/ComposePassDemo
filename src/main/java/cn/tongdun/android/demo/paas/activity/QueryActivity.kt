package cn.tongdun.android.demo.paas.activity

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.tongdun.android.demo.paas.Constant
import cn.tongdun.android.demo.paas.Constant.PARAMS_BLACK_BOX
import cn.tongdun.android.demo.paas.Constant.PARAMS_EVENT_ID
import cn.tongdun.android.demo.paas.Constant.PARAMS_PARTNER_CODE
import cn.tongdun.android.demo.paas.Constant.PARAMS_RESP_DETAIL_TYPE
import cn.tongdun.android.demo.paas.bean.FraudApiResponse
import cn.tongdun.android.demo.paas.bean.TField
import cn.tongdun.android.demo.paas.pages.LoadingDialog
import cn.tongdun.android.demo.paas.util.showToast
import cn.tongdun.android.demo.paas.viewmodel.DataModel
import com.example.composektdemo.ui.theme.PaasDemoTheme
import java.util.*

/**
 * @Author yang.bai.
 * Date: 2023/2/3
 */
class QueryActivity : BaseComponentActivity() {
    lateinit var blackBox: String
    lateinit var prefixUrl: String
    private var dialogShowState: MutableState<Boolean>? = null

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun initView() {

        intent!!.run {
            blackBox = getStringExtra("blackBox")!!
            prefixUrl = getStringExtra("prefix_url")!!
            if (TextUtils.isEmpty(prefixUrl)) {
                "url不合法".showToast()
                finish()
            }

            if (TextUtils.isEmpty(blackBox)) {
                "blackBox为空".showToast()
                finish()
            }
        }
        setContent {
            PaasDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Content()
                    dialogShowState = remember {
                        mutableStateOf(false)
                    }
                    LoadingDialog(dialogShowState = dialogShowState!!)
                }
            }
        }

    }

    @Composable
    fun Content(dataModel: DataModel = viewModel()) {
        dataModel.getConfig(Constant.PARTNER_CODE).observeAsState().value?.let { config ->
            var params: MutableMap<String, String> = HashMap<String, String>()
            params[PARAMS_PARTNER_CODE] = config.value // 此处值填写您的合作方标识
            params[PARAMS_EVENT_ID] = "android_login" // 此处填写策略集上的事件标识
            params[PARAMS_RESP_DETAIL_TYPE] = "device"
            params[PARAMS_BLACK_BOX] = blackBox //此处填写移动端sdk采集到的信息black_box
            params[PARAMS_RESP_DETAIL_TYPE] = "device"

            dialogShowState?.value = true
            dataModel.let {
                it.url = prefixUrl + "/restricted/androidQuery.json"
                it.params = params
                it.queryData.observeAsState().value?.let {
                    it.getOrNull()?.let { data ->
                        ItemListView(processData(data))
                        dialogShowState?.value = false
                    }
                }
            }
        }
    }

    @Composable
    private fun ItemListView(data: MutableList<TField>?, dataModel: DataModel = viewModel()) {
        data?.let {
            LazyColumn {
                items(it) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(all = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.key,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            fontSize = 15.sp
                        )
                        Text(
                            text = item.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp)
                                .weight(2f),
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }

    private fun processData(result: FraudApiResponse): MutableList<TField>? {
        if ("000" != result.code) {
            result.code.showToast()
            dialogShowState?.value = false
            return null
        }
        //设备详情
        val deviceInfo = result.result
        val data: MutableList<TField> =
            ArrayList<TField>()
        val tmp: MutableList<TField> =
            ArrayList<TField>()

        setFirstData(deviceInfo, data)
        val entries: Set<*> = deviceInfo.entries
        val iterator = entries.iterator()
        while (iterator.hasNext()) {
            val (key, value) = iterator.next() as Map.Entry<*, *>
            if (key == "geoIp") continue
            val item: TField = TField()
            item.key = key as String
            item.value = value.toString()
            tmp.add(item)
        }
        Collections.sort(tmp,
            Comparator<TField> { o1: TField, o2: TField ->
                val key1: String = o1.key.toString()
                val key2: String = o2.key.toString()
                val value = key1.compareTo(key2)
                if (value < 0) -1 else if (value == 0) 0 else 1
            })

        data.addAll(tmp)
        return data
        return null
    }

    fun setFirstData(deviceInfo: HashMap<String, Any>, data: MutableList<TField>) {
        val deviceId = "deviceId"
        val wifiIp = "wifiIp"
        val wifiMac = "wifiMac"
        val vpnIp = "vpnIp"
        val root = "root"
        val imei = "imei"
        val netWorkType = "networkType"
        val model = "model"
        val bluemac = "blueMac"
        val imsi = "imsi"
        val androidId = "androidId"
        val exceptioninfo = "exceptionInfo"
        val xdeviceid = "xDeviceId"
        if (deviceInfo.containsKey(deviceId)) {
            val item: TField = TField()
            item.key = deviceId
            item.value = deviceInfo[deviceId] as String
            data.add(item)

            deviceInfo.remove(deviceId)
        }
        if (deviceInfo.containsKey(xdeviceid)) {
            val item: TField = TField()
            item.key = xdeviceid
            item.value = deviceInfo[xdeviceid] as String
            data.add(item)
            deviceInfo.remove(xdeviceid)
        }
        if (deviceInfo.containsKey(wifiIp)) {
            val item: TField = TField()
            item.key = wifiIp
            item.value = deviceInfo[wifiIp] as String
            data.add(item)
            deviceInfo.remove(wifiIp)
        }
        if (deviceInfo.containsKey(wifiMac)) {
            val item: TField = TField()
            item.key = wifiMac
            item.value = deviceInfo[wifiMac] as String
            data.add(item)
            deviceInfo.remove(wifiMac)
        }
        if (deviceInfo.containsKey(root)) {
            val item: TField = TField()
            item.key = root
            item.value = "" + deviceInfo[root].toString()
            data.add(item)
            deviceInfo.remove(root)
        }
        if (deviceInfo.containsKey(imsi)) {
            val item: TField = TField()
            item.key = imsi
            item.value = deviceInfo[imsi] as String
            data.add(item)
            deviceInfo.remove(imsi)
        }
        if (deviceInfo.containsKey(imei)) {
            val item: TField = TField()
            item.key = imei
            item.value = deviceInfo[imei] as String
            data.add(item)
            deviceInfo.remove(imei)
        }
        if (deviceInfo.containsKey(netWorkType)) {
            val item: TField = TField()
            item.key = netWorkType
            item.value = deviceInfo[netWorkType] as String
            data.add(item)
            deviceInfo.remove(netWorkType)
        }
        if (deviceInfo.containsKey(model)) {
            val item: TField = TField()
            item.key = model
            item.value = deviceInfo[model] as String
            data.add(item)
            deviceInfo.remove(model)
        }
        if (deviceInfo.containsKey(bluemac)) {
            val item: TField = TField()
            item.key = bluemac
            item.value = deviceInfo[bluemac] as String
            data.add(item)
            deviceInfo.remove(bluemac)
        }
        if (deviceInfo.containsKey(vpnIp)) {
            val item: TField = TField()
            item.key = vpnIp
            item.value = deviceInfo[vpnIp] as String
            data.add(item)
            deviceInfo.remove(vpnIp)
        }
        if (deviceInfo.containsKey(androidId)) {
            val item: TField = TField()
            item.key = androidId
            item.value = deviceInfo[androidId] as String
            data.add(item)
            deviceInfo.remove(androidId)
        }
        if (deviceInfo.containsKey(exceptioninfo)) {
            val item: TField = TField()
            item.key = exceptioninfo
            item.value = deviceInfo[exceptioninfo] as String
            data.add(item)
            deviceInfo.remove(exceptioninfo)
        }
    }
}