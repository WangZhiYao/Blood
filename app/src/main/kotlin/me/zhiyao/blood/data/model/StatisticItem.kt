package me.zhiyao.blood.data.model

import me.zhiyao.blood.constants.BloodPressureType

/**
 *
 * @author WangZhiYao
 * @date 2021/2/28
 */
data class StatisticItem(
    val type: BloodPressureType,
    val data: List<Int>
)