package me.zhiyao.blood.data.api.request

import me.zhiyao.blood.data.db.model.BloodPressure

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
data class UploadRequest(
    val bloodPressureList: List<BloodPressure>
)
