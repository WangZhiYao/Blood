package me.zhiyao.blood.ui.main.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import me.zhiyao.blood.constants.BloodPressureType
import me.zhiyao.blood.data.db.model.BloodPressure
import me.zhiyao.blood.data.model.DailyAverage
import me.zhiyao.blood.data.model.StatisticItem
import me.zhiyao.blood.data.model.StatisticResult
import me.zhiyao.blood.data.repo.BloodPressureRepository
import me.zhiyao.blood.util.TimeUtils
import java.util.*
import javax.inject.Inject

/**
 *
 * @author WangZhiYao
 * @date 2021/2/24
 */
@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val bloodPressureRepository: BloodPressureRepository
) : ViewModel() {

    fun getDailyAverageStatisticResult(
        startTime: Long,
        endTime: Long = Calendar.getInstance().timeInMillis
    ) = liveData {
        val dateList = ArrayList<String>()
        val statisticItemList = ArrayList<StatisticItem>()
        val recordMapByDate = LinkedHashMap<String, ArrayList<BloodPressure>>()

        bloodPressureRepository.getBloodPressureRecordList(startTime, endTime)
            .forEach { bloodPressure ->
                // 先按日分配
                val date = TimeUtils.timestamp2Str(TimeUtils.YYYY_MM_DD, bloodPressure.measureTime)
                val recordList = if (recordMapByDate.containsKey(date)) {
                    recordMapByDate[date]!!
                } else {
                    ArrayList()
                }.apply {
                    recordMapByDate[date] = this
                }

                recordList.add(bloodPressure)
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

        emit(
            StatisticResult(dateList, statisticItemList)
        )
    }
}