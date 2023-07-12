package cn.tongdun.android.demo.paas.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @Author yang.bai.
 * Date: 2023/1/30
 */
@Dao
interface ConfigDao {
    @Query("SELECT * FROM config")
    fun getAllConfig(): LiveData<List<Config>>

    @Query("SELECT * FROM config")
    fun getSuspendAllConfig(): List<Config>

    @Query("SELECT * FROM config WHERE `key`  = :key")
    fun getConfig(key: String): LiveData<Config>

    @Query("SELECT * FROM config WHERE `key`  = :key")
    fun getSuspendConfig(key: String): Config

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(config: Config)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Config>)

    @Delete
    fun delete(config: Config)

    @Update
    fun update(config: Config)
}