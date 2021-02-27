package me.zhiyao.blood.ui.main.record.listener

import me.zhiyao.blood.data.db.model.BloodPressure

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
interface OnRecordClickListener {

    fun onRecordClicked(bloodPressure: BloodPressure)

}