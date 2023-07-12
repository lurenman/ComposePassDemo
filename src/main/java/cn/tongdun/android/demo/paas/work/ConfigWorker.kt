package cn.tongdun.android.demo.paas.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import cn.tongdun.android.demo.paas.App.Companion.context
import cn.tongdun.android.demo.paas.repository.DataRepository
import cn.tongdun.android.demo.paas.util.generateConfigs

/**
 * @Author yang.bai.
 * Date: 2023/1/31
 * 默认初始化配置
 */
class ConfigWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            DataRepository.insert(generateConfigs(ctx = context))
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}