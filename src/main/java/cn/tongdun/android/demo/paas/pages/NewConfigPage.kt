package cn.tongdun.android.demo.paas.pages

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.tongdun.android.demo.paas.App.Companion.context
import cn.tongdun.android.demo.paas.Constant
import cn.tongdun.android.demo.paas.R
import cn.tongdun.android.demo.paas.activity.QueryActivity
import cn.tongdun.android.demo.paas.db.Config
import cn.tongdun.android.demo.paas.util.longClick
import cn.tongdun.android.demo.paas.util.showToast
import cn.tongdun.android.demo.paas.view.PaasButton
import cn.tongdun.android.demo.paas.viewmodel.NewConfigPageModel
import cn.tongdun.android.shell.FMAgent
import kotlinx.coroutines.launch

/**
 * @Author yang.bai.
 * Date: 2023/1/30
 * 新配置界面
 */
private const val TAG = "NewConfigPage"
private var blackboxValue: MutableState<String>? = null
private var blackboxViewVisible: MutableState<Boolean>? = null
private var dialogShowState: MutableState<Boolean>? = null
private var urlState: MutableState<String>? = null

@Composable
fun NewConfigPage(context: Context) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 20.dp)
        ) {
            Content(context = context)
        }
    }
    dialogShowState = remember {
        mutableStateOf(false)
    }
    LoadingDialog(dialogShowState = dialogShowState!!)
}

@Composable
private fun Content(context: Context, model: NewConfigPageModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "url:")

        urlState = remember {
            mutableStateOf(context.getString(R.string.default_url))
        }
        model.getConfig(Constant.CUSTOM_URL_NEW).observeAsState().value?.let {
            val sbValue = it.value.substring(0, it.value.indexOf("/android3_8/profile.json"))
            urlState!!.value = sbValue
        }
        TextField(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .background(color = colorResource(id = R.color.white))
                .padding(start = 10.dp, top = 0.dp),
            value = urlState!!.value,
            onValueChange = {
                urlState!!.value = it
                coroutineScope.launch {
                    model.update(
                        Config(
                            Constant.CUSTOM_URL_NEW,
                            urlState!!.value + "/android3_8/profile.json"
                        )
                    )
                }
            },
            singleLine = true,
        )
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
    Button(
        onClick = {
            coroutineScope.launch {
                callBack(model)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
    ) {
        Text(text = "CALLBACK")
    }
    Button(
        onClick = {
            coroutineScope.launch {
                callBackNoConfig(model)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
    ) {
        Text(text = "CALLBACK(不带配置)")
    }
    Button(
        onClick = {
            coroutineScope.launch {
                model.checkObfuscate()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
    ) {
        Text(text = "混淆检测")
    }
    PaasButton(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 5.dp)
            .longClick {
                query(model)
            }

    ) {
        Text(text = "查询")
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
    blackboxViewVisible = remember {
        mutableStateOf(false)
    }
    //blackbox view
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(all = 5.dp)
            .alpha(
                if (blackboxViewVisible!!.value) {
                    1f
                } else {
                    0f
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        blackboxValue = remember {
            mutableStateOf("")
        }
        Text(text = "blackbox:", fontSize = 25.sp, modifier = Modifier.fillMaxWidth())
        BasicTextField(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(all = 5.dp),
            textStyle = TextStyle(fontSize = 15.sp),
            value = blackboxValue!!.value,
            readOnly = true,
            onValueChange = { }
        )
    }
}

/**
 * 带配置的调用
 */
private suspend fun callBack(model: NewConfigPageModel) {
    dialogShowState?.value = true
    val configList = model.getCoroutineScopeAllConfig()
    FMAgent.openLog()
    val optionConfigBuilder = model.getOptionConfigBuilder(configList)
    val option = optionConfigBuilder
        //.production(false)
        .callback { blackBox -> // 注意这里不是主线程 请不要在这个函数里进行ui操作，否则可能会出现崩溃
            Log.d(TAG, "call back blackbox = $blackBox")
            blackboxValue?.value = blackBox
            blackboxViewVisible?.value = true
            dialogShowState?.value = false
        }.build()
    FMAgent.init(context, option)
}

/**
 * 添加配置的调用api，必传url
 */
private suspend fun callBackNoConfig(model: NewConfigPageModel) {
    dialogShowState?.value = true
    val collectUrl = model.getCoroutineScopeConfig(Constant.CUSTOM_URL_NEW).value
    FMAgent.openLog()
    val optionConfigBuilder = FMAgent.Builder().url(collectUrl)
    val option = optionConfigBuilder
        .callback { blackBox -> // 注意这里不是主线程 请不要在这个函数里进行ui操作，否则可能会出现崩溃
            Log.d(TAG, "call back no config blackbox = $blackBox")
            blackboxValue?.value = blackBox
            blackboxViewVisible?.value = true
            dialogShowState?.value = false
        }.build()
    FMAgent.init(context, option)
}

private fun query(model: NewConfigPageModel) {
    if (!TextUtils.isEmpty(blackboxValue!!.value)) {
        val intent = Intent(context, QueryActivity::class.java).apply {
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("blackBox", blackboxValue!!.value)
            putExtra("prefix_url", urlState!!.value)
        }
        context.startActivity(intent)
    } else {
        "请先获取blackBox".showToast()
    }
}