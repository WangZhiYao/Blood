package me.zhiyao.blood.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.zhiyao.blood.data.db.model.BloodPressure

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
@Dao
interface BloodPressureDao {

    @Insert
    suspend fun insert(vararg bloodPressure: BloodPressure)

    @Query("SELECT * FROM t_blood_pressure_record ORDER BY measure_time DESC")
    fun queryBloodPressureRecordList(): PagingSource<Int, BloodPressure>

    @Delete
    suspend fun delete(vararg bloodPressure: BloodPressure)
}