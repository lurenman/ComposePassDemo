package cn.tongdun.android.demo.paas.activity

import android.annotation.SuppressLint
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.tongdun.android.demo.paas.R
import cn.tongdun.android.demo.paas.pages.DrawerPage
import cn.tongdun.android.demo.paas.pages.TabViewPager
import com.example.composektdemo.ui.theme.PaasDemoTheme
import com.example.composektdemo.ui.theme.Purple700
import kotlinx.coroutines.launch

/**
 * @Author yang.bai.
 * Date: 2023/1/17
 */
class MainActivity : BaseComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        PaasDemoTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background,
            ) {
                val scaffoldState =
                    rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
                val coroutineScope = rememberCoroutineScope()
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopBar(
                            onMenuClicked = {
                                coroutineScope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            })
                    },
                    drawerContent = {
                        DrawerPage()
                    },
                ) {
                    Content()
                }
            }
        }
    }

    @Composable
    fun TopBar(onMenuClicked: () -> Unit) {
        TopAppBar(
            title = {
                Text(text = mContext.getString(R.string.app_name), color = Color.White)
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier.clickable(onClick = onMenuClicked),
                    tint = Color.White
                )
            },
            backgroundColor = Purple700,
            elevation = 12.dp
        )
    }

    @Composable
    fun Content() {
        TabViewPager(mContext)
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun initView() {
        setContent {
            PaasDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val scaffoldState =
                        rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
                    val coroutineScope = rememberCoroutineScope()
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            TopBar(
                                onMenuClicked = {
                                    coroutineScope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                })
                        },
                        drawerContent = {
                            DrawerPage()
                        },
                    ) {
                        Content()
                    }
                }
            }
        }
    }
}