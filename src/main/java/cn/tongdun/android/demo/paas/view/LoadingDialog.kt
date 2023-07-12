package cn.tongdun.android.demo.paas.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * @Author yang.bai.
 * Date: 2023/2/3
 * 加载进度对话框
 */


@Composable
fun LoadingDialog(dialogShowState: MutableState<Boolean>) {
    if (!dialogShowState.value)
        return
    Dialog(onDismissRequest = { dialogShowState.value = false }) {
        Surface(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(all = 20.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = Color.White),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Loading.....", modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .padding(start = 20.dp, top = 10.dp)
                    )
                    CircularProgressIndicator(
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                            .padding(start = 20.dp, top = 18.dp, bottom = 20.dp)
                    )
                }
            }
        }
    }
}