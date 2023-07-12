package cn.tongdun.android.demo.paas.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import cn.tongdun.android.demo.paas.Constant
import cn.tongdun.android.demo.paas.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author yang.bai.
 * Date: 2023/1/30
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseComponentActivity() {
    override fun initView() {
        launch {
            if (Constant.DEBUG) {
                setContent {
                    Surface(
                        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                    ) {
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn() + expandIn(),
                            exit = shrinkOut() + fadeOut()
                        ) {
                            Image(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "logo"
                            )
                        }
                    }
                }
                redirectTo(true)
            } else {
                redirectTo()
            }
        }
    }

    private suspend fun redirectTo(isDelay: Boolean = false) = withContext(Dispatchers.IO) {
        if (isDelay) delay(1500)
        launch {
            Intent(mContext, MainActivity::class.java).run {
                startActivity(this)
                finish()
            }
        }
    }
}