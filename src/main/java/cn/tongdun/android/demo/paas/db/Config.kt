package cn.tongdun.android.demo.paas.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author yang.bai.
 * Date: 2023/1/30
 */
@Entity(tableName = "config")
data class Config(
   // @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "key") @PrimaryKey val key: String,
    @ColumnInfo(name = "value") var value: String
)