package cn.tongdun.android.demo.paas.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cn.tongdun.android.demo.paas.db.Config
import cn.tongdun.android.demo.paas.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


/**
 * @Author yang.bai.
 * Date: 2023/2/1
 * todo 寻找更好的方法
 */
open class DataModel : ViewModel() {
    companion object {
        const val TAG = "DataModel"
    }

    val allConfigs = DataRepository.getAllConfig()

    var url = ""
    var params: MutableMap<String, String> = HashMap()
    val queryData = getCoroutineLiveData(Dispatchers.IO) {
        val result = DataRepository.queryBlackboxInfo(url, params)
        Result.success(result)
    }

    fun getConfig(key: String): LiveData<Config> {
        return DataRepository.getConfig(key)
    }

    suspend fun getCoroutineScopeAllConfig() = withContext(Dispatchers.IO) {
        DataRepository.getCoroutineScopeAllConfig()
    }

    suspend fun getCoroutineScopeConfig(key: String) = withContext(Dispatchers.IO) {
        DataRepository.getCoroutineScopeConfig(key)
    }

    suspend fun update(config: Config) = withContext(Dispatchers.IO) {
        DataRepository.update(config)
    }

    suspend fun insert(list: List<Config>) = withContext(Dispatchers.IO) {
        DataRepository.insert(list)
    }

    /**
     * 获取支持协程的CoroutineLiveData
     */
    fun <T> getCoroutineLiveData(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Log.e(TAG, "error getCoroutineLiveData: " + e.message)
                Result.failure(e)
            }
            //通知数据变化
            emit(result)
        }
}