package me.zhiyao.blood.ui.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.zhiyao.blood.data.db.model.BloodPressure
import me.zhiyao.blood.data.repo.BloodPressureRepository
import javax.inject.Inject

/**
 *
 * @author WangZhiYao
 * @date 2021/2/27
 */
@HiltViewModel
class NewRecordViewModel @Inject constructor(
    private val bloodPressureRepository: BloodPressureRepository
) : ViewModel() {

    fun insertBloodPressure(bloodPressure: BloodPressure) {
        viewModelScope.launch {
            bloodPressureRepository.insertBloodPressure(bloodPressure)
        }
    }
}