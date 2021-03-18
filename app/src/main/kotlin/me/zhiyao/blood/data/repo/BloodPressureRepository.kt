package me.zhiyao.blood.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import me.zhiyao.blood.data.db.dao.BloodPressureDao
import me.zhiyao.blood.data.db.model.BloodPressure
import javax.inject.Inject

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
class BloodPressureRepository @Inject constructor(
    private val bloodPressureDao: BloodPressureDao
) {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 3,
        enablePlaceholders = false,
        initialLoadSize = 20
    )

    fun getBloodPressureRecordList() = Pager(pagingConfig) {
        bloodPressureDao.queryBloodPressureRecordList()
    }.flow

    suspend fun getBloodPressureRecordList(startTime: Long, endTime: Long) =
        bloodPressureDao.queryBloodPressureRecordList(startTime, endTime)

    suspend fun insertBloodPressure(bloodPressure: BloodPressure) {
        bloodPressureDao.insert(bloodPressure)
    }

    suspend fun deleteBloodPressure(bloodPressure: BloodPressure) {
        bloodPressureDao.delete(bloodPressure)
    }
}