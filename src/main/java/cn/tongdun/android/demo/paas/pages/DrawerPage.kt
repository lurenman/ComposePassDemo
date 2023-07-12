package cn.tongdun.android.demo.paas.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.tongdun.android.demo.paas.App
import cn.tongdun.android.demo.paas.ConfigItem
import cn.tongdun.android.demo.paas.R
import cn.tongdun.android.demo.paas.util.generateConfigs
import cn.tongdun.android.demo.paas.util.showToast
import cn.tongdun.android.demo.paas.viewmodel.DataModel
import com.example.composektdemo.ui.theme.Purple500
import kotlinx.coroutines.launch

/**
 * @Author yang.bai.
 * Date: 2023/1/31
 */
private var stateList: SnapshotStateList<ConfigItem>? = null

@Composable
fun DrawerPage() {
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        DrawerHeader()
        SettingInfo()
    }
}

@Composable
fun DrawerHeader(
    drawerModel: DataModel = viewModel(),
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(Purple500)
            .wrapContentHeight()
            .padding(start = 5.dp)
    ) {
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(all = 3.dp),
            text = " 设置", color = Color.White
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = modifier
                .weight(1f)
                .wrapContentHeight()
        ) {
            val coroutineScope = rememberCoroutineScope()
            Button(modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(all = 3.dp),
                onClick = {
                    coroutineScope.launch {
                        val generateConfigs = generateConfigs(ctx = App.context)
                        drawerModel.insert(generateConfigs)
                        " 重置成功".showToast()
                    }
                }
            ) {
                Text(text = " 重置", color = Color.White)
            }
        }
    }
}


@Composable
fun SettingInfo(drawerModel: DataModel = viewModel()) {
    stateList = remember { mutableStateListOf<ConfigItem>() }
    drawerModel.allConfigs.observeAsState().value?.let { configList ->
        val itemList = mutableListOf<ConfigItem>()
        configList.forEach {
            itemList.add(ConfigItem(it))
        }
        stateList?.clear()
        stateList?.addAll(itemList.sortedBy {
            it.viewType.value
        })
        LazyColumn {
            items(stateList!!) { configItem ->
                when (configItem.viewType) {
                    ConfigItem.ViewType.SET -> ItemSetView(configItem = configItem)
                    ConfigItem.ViewType.SWITCH -> ItemSwitchView(configItem = configItem)
                    else -> {
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemSetView(drawerModel: DataModel = viewModel(), configItem: ConfigItem) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .height(1.2.dp)
            .background(color = colorResource(id = R.color.divider))
    )
    Row(
        modifier = Modifier.padding(start = 8.dp, top = 3.dp, bottom = 3.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val mValue = remember {
            mutableStateOf(configItem.data.value)
        }
        BasicTextField(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .background(
                    MaterialTheme.colors.surface,
                    MaterialTheme.shapes.small,
                )
                .weight(0.5f),
            value = mValue.value,
            onValueChange = { mValue.value = it },
            singleLine = true,
        )
        val coroutineScope = rememberCoroutineScope()
        Button(
            onClick = {
                coroutineScope.launch {
                    configItem.data.value = mValue.value
                    drawerModel.update(configItem.data)
                    (configItem.uitext + "写入成功").showToast()
                }
            }, modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 20.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
        ) {
            Text(text = configItem.uitext, fontSize = 12.sp)
        }
    }
}

@Composable
private fun ItemSwitchView(drawerModel: DataModel = viewModel(), configItem: ConfigItem) {
    Row(
        modifier = Modifier.padding(start = 8.dp, top = 3.dp, bottom = 3.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.padding(start = 5.dp), text = configItem.uitext, fontSize = 12.sp)

        val checked = remember {
            if (configItem.dataType != ConfigItem.DataType.BOOLEAN)
                throw RuntimeException("the paas demo config data type error from key ${configItem.data.key}")
            mutableStateOf(configItem.data.value.toBoolean())
        }
        val coroutineScope = rememberCoroutineScope()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Switch(checked = checked.value, onCheckedChange = {
                checked.value = it
                coroutineScope.launch {
                    configItem.data.value = checked.value.toString()
                    drawerModel.update(configItem.data)
                    if (checked.value) {
                        (configItem.uitext + "已经开启了").showToast()
                    } else {
                        (configItem.uitext + "已经关闭了").showToast()
                    }
                }
            })
        }
    }
}