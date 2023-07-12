package cn.tongdun.android.demo.paas.pages

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.tongdun.android.demo.paas.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Author yang.bai.
 * Date: 2023/1/30
 */
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabViewPager(context: Context) {
    Column(modifier = Modifier.fillMaxSize()) {
        val pages by mutableStateOf(
            listOf("新API", "老API")
        )
        val pagerState = rememberPagerState(initialPage = 0)//初始化页面，0就表示第一个页面
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            // 使用提供的 pagerTabIndicatorOffset 修饰符自定义指示器
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
            backgroundColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.purple_500)
        ) {
            //给全部页面添加标签栏
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title, fontSize = 16.sp) },
                    selected = pagerState.currentPage == index,//是否选中
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                    modifier = Modifier.alpha(0.9f),//透明度
                    enabled = true,//是否启用
                    selectedContentColor = colorResource(id = R.color.purple_500),//选中的颜色
                    unselectedContentColor = colorResource(id = R.color.black),//未选中的颜色
                )
            }
        }
        HorizontalPager(
            count = pages.size,
            state = pagerState,//用于控制或观察viewpage状态的状态对象。
            modifier = Modifier.padding(start = 10.dp, top = 4.dp, end = 10.dp),
            itemSpacing = 2.dp
        ) { page ->
            when (page) {
                0 -> {
                    NewConfigPage(context)
                }
                1 -> {
                    OldConfigPage(context)
                }
            }
        }
    }
}