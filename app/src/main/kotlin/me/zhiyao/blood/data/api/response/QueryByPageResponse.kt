package me.zhiyao.blood.data.api.response

import me.zhiyao.blood.data.db.model.BloodPressure

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
data class QueryByPageResponse(val data: List<BloodPressure>) : BaseResponse()
