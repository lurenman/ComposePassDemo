package cn.tongdun.android.demo.paas.net

import android.text.TextUtils
import cn.tongdun.android.demo.paas.App.Companion.context
import cn.tongdun.android.demo.paas.R
import cn.tongdun.android.demo.paas.bean.FraudApiResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @Author yang.bai.
 * Date: 2023/2/6
 */
object RetrofitManager {
    val mBaseUrl by lazy {
        context.getString(R.string.default_url)
    }

    private val mService by lazy {
        create(ApiService::class.java)
    }

    private fun getRetrofit(url: String): Retrofit {
        if (TextUtils.isEmpty(url))
            throw RuntimeException("Network request url cannot be empty!")
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun <T> create(serviceClass: Class<T>): T = getRetrofit(mBaseUrl).create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

    suspend fun queryBlackboxInfo(url: String, data: Map<String, String>): FraudApiResponse =
        mService.queryBlackboxInfo(url, data = data).await()
}