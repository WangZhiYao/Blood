package me.zhiyao.blood.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import me.zhiyao.blood.constants.BloodPressureType
import me.zhiyao.blood.data.db.dao.BloodPressureDao
import me.zhiyao.blood.data.db.model.BloodPressure
import me.zhiyao.blood.data.model.DailyAverage
import me.zhiyao.blood.data.model.StatisticItem
import me.zhiyao.blood.data.model.StatisticResult
import me.zhiyao.blood.util.TimeUtils
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

    suspend fun getDailyStatisticResult(startTime: Long, endTime: Long): StatisticResult {
        val dateList = ArrayList<String>()
        val statisticItemList = ArrayList<StatisticItem>()
        val recordMapByDate = LinkedHashMap<String, ArrayList<BloodPressure>>()

        bloodPressureDao.queryBloodPressureRecordList(startTime, endTime)
            .forEach {
                // 先按日分配
                val date = TimeUtils.timestamp2Str(TimeUtils.YYYY_MM_DD, it.measureTime)
                val recordList = if (recordMapByDate.containsKey(date)) {
                    recordMapByDate[date]!!
                } else {
                    ArrayList()
                }.apply {
                    recordMapByDate[date] = this
                }

                recordList.add(it)
            }

        val dailyAverageMap = LinkedHashMap<String, DailyAverage>()
        if (recordMapByDate.isNotEmpty()) {
            // 取日平均
            recordMapByDate.forEach { entry ->
                var dailyTotalSys = 0
                var dailyTotalDia = 0
                var dailyTotalPul = 0

                entry.value.run {
                    forEach { bloodPressure ->
                        dailyTotalSys += bloodPressure.sys
                        dailyTotalDia += bloodPressure.dia
                        dailyTotalPul += bloodPressure.pul
                    }

                    dailyAverageMap[entry.key] = DailyAverage(
                        dailyTotalSys.div(size),
                        dailyTotalDia.div(size),
                        dailyTotalPul.div(size)
                    )
                }
            }

            // 按 sys, dia, pul 分类
            val sysList = ArrayList<Int>()
            val diaList = ArrayList<Int>()
            val pulList = ArrayList<Int>()

            dailyAverageMap.forEach {
                dateList.add(it.key)
                sysList.add(it.value.sys)
                diaList.add(it.value.dia)
                pulList.add(it.value.pul)
            }

            statisticItemList.add(StatisticItem(BloodPressureType.SYS, sysList))
            statisticItemList.add(StatisticItem(BloodPressureType.DIA, diaList))
            statisticItemList.add(StatisticItem(BloodPressureType.PUL, pulList))
        }

        return StatisticResult(dateList, statisticItemList)
    }

    suspend fun insertBloodPressure(bloodPressure: BloodPressure) {
        bloodPressureDao.insert(bloodPressure)
    }

    suspend fun deleteBloodPressure(bloodPressure: BloodPressure) {
        bloodPressureDao.delete(bloodPressure)
    }
}