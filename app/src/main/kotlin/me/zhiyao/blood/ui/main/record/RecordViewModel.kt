package me.zhiyao.blood.ui.main.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.zhiyao.blood.data.db.model.BloodPressure
import me.zhiyao.blood.data.repo.BloodPressureRepository
import javax.inject.Inject

/**
 *
 * @author WangZhiYao
 * @date 2021/2/24
 */
@HiltViewModel
class RecordViewModel @Inject constructor(
    private val bloodPressureRepository: BloodPressureRepository
) : ViewModel() {

    val bloodPressureRecordList = bloodPressureRepository.getBloodPressureRecordList()
        .cachedIn(viewModelScope)
        .asLiveData()

    fun deleteRecord(bloodPressure: BloodPressure) {
        viewModelScope.launch {
            bloodPressureRepository.deleteBloodPressure(bloodPressure)
        }
    }

    fun insertRecord(bloodPressure: BloodPressure) {
        viewModelScope.launch {
            bloodPressureRepository.insertBloodPressure(bloodPressure)
        }
    }
}