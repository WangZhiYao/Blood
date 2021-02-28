package me.zhiyao.blood.ui.main.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import me.zhiyao.blood.data.repo.BloodPressureRepository
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

    fun getDailyStatisticResult(
        startTime: Long,
        endTime: Long = System.currentTimeMillis()
    ) = liveData {
        emit(
            bloodPressureRepository
                .getDailyStatisticResult(startTime, endTime)
        )
    }
}