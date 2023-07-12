package cn.tongdun.android.demo.paas.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import cn.tongdun.android.demo.paas.work.ConfigWorker

/**
 * @Author yang.bai.
 * Date: 2023/1/30
 */
@Database(
    entities = [Config::class],
    version = 1,
    exportSchema = false
)
abstract class ConfigDb : RoomDatabase() {
    abstract fun configDao(): ConfigDao

    companion object {
        private const val TAG = "ConfigDb"
        private const val DATABASE_NAME = "paas_config"

        @Volatile
        private var instance: ConfigDb? = null

        fun getInstance(context: Context): ConfigDb {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context)
                    .also {
                        instance = it
                    }
            }
        }

        /**
         * 针对回调onCreate回调时机是在dao方法调用时才会触发
         */
        private fun buildDataBase(context: Context): ConfigDb {
            return Room.databaseBuilder(context, ConfigDb::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //插入默认配置数据
                        val request: OneTimeWorkRequest =
                            OneTimeWorkRequestBuilder<ConfigWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
        }
    }
}