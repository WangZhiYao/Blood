package me.zhiyao.blood.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.zhiyao.blood.data.db.dao.BloodPressureDao
import me.zhiyao.blood.data.db.model.BloodPressure

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
@Database(
    entities = [
        BloodPressure::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bloodPressureDao(): BloodPressureDao
}