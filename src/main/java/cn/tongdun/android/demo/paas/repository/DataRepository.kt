package cn.tongdun.android.demo.paas.repository

import androidx.lifecycle.LiveData
import cn.tongdun.android.demo.paas.App
import cn.tongdun.android.demo.paas.bean.FraudApiResponse
import cn.tongdun.android.demo.paas.db.Config
import cn.tongdun.android.demo.paas.net.RetrofitManager

/**
 * @Author yang.bai.
 * Date: 2023/1/31
 */
object DataRepository {
    fun getAllConfig(): LiveData<List<Config>> = App.db.configDao().getAllConfig()

    suspend fun queryBlackboxInfo(url: String, data: Map<String, String>): FraudApiResponse =
        RetrofitManager.queryBlackboxInfo(url, data)

    suspend fun getCoroutineScopeAllConfig(): List<Config> =
        App.db.configDao().getSuspendAllConfig()

    fun getConfig(key: String): LiveData<Config> = App.db.configDao().getConfig(key)

    suspend fun getCoroutineScopeConfig(key: String): Config =
        App.db.configDao().getSuspendConfig(key)

    suspend fun insert(list: List<Config>) = App.db.configDao().insert(list)

    suspend fun insert(config: Config) = App.db.configDao().insert(config)

    suspend fun update(config: Config) = App.db.configDao().update(config)

    suspend fun delete(config: Config) = App.db.configDao().delete(config)
}