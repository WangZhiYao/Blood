package me.zhiyao.blood.ui.main.record.viewholder

import androidx.recyclerview.widget.RecyclerView
import me.zhiyao.blood.R
import me.zhiyao.blood.constants.BloodPressureLevel
import me.zhiyao.blood.data.db.model.BloodPressure
import me.zhiyao.blood.databinding.ItemBloodPressureBinding
import me.zhiyao.blood.util.TimeUtils

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
class RecordViewHolder(
    private val binding: ItemBloodPressureBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(bloodPressure: BloodPressure) {
        binding.tvSys.text = bloodPressure.sys.toString()
        binding.tvDia.text = bloodPressure.dia.toString()
        binding.tvPul.text = bloodPressure.pul.toString()

        binding.tvMeasureTime.text = TimeUtils.timestamp2Str(bloodPressure.measureTime)

        val sysLevel = BloodPressureLevel.values().find {
            it.minSys <= bloodPressure.sys && it.maxSys >= bloodPressure.sys
        }

        val diaLevel = BloodPressureLevel.values().find {
            it.minDia <= bloodPressure.dia && it.maxDia >= bloodPressure.dia
        }

        if (sysLevel == null || diaLevel == null) {
            binding.tvResult.setText(R.string.blood_pressure_level_wrong)
            binding.vSign.setBackgroundResource(R.color.colorWrong)
            return
        }

        if (sysLevel == diaLevel) {
            setResult(sysLevel)
        } else {
            if (diaLevel == BloodPressureLevel.NORMAL) {
                setResult(sysLevel)
                return
            }

            if (sysLevel == BloodPressureLevel.NORMAL) {
                setResult(diaLevel)
                return
            }

            BloodPressureLevel.values().forEach {
                if (it == sysLevel || it == diaLevel) {
                    setResult(it)
                }
            }

            val resultBuilder = StringBuilder()
            resultBuilder
                .append(binding.tvResult.context.getString(R.string.blood_pressure_sys))
                .append("：")
            appendResult(sysLevel, resultBuilder)

            resultBuilder
                .append(" ")
                .append(binding.tvResult.context.getString(R.string.blood_pressure_dia))
                .append("：")
            appendResult(diaLevel, resultBuilder)

            binding.tvResult.text = resultBuilder.toString()
        }
    }

    private fun setResult(bloodPressureLevel: BloodPressureLevel) {
        when (bloodPressureLevel) {
            BloodPressureLevel.LOW -> {
                binding.tvResult.setText(R.string.blood_pressure_level_low)
                binding.vSign.setBackgroundResource(R.color.colorLow)
            }
            BloodPressureLevel.NORMAL_LOW -> {
                binding.tvResult.setText(R.string.blood_pressure_level_normal_low)
                binding.vSign.setBackgroundResource(R.color.colorNormalLow)
            }
            BloodPressureLevel.NORMAL -> {
                binding.tvResult.setText(R.string.blood_pressure_level_normal)
                binding.vSign.setBackgroundResource(R.color.colorNormal)
            }
            BloodPressureLevel.NORMAL_HIGH -> {
                binding.tvResult.setText(R.string.blood_pressure_level_normal_high)
                binding.vSign.setBackgroundResource(R.color.colorNormalHigh)
            }
            BloodPressureLevel.HIGH_LEVEL_1 -> {
                binding.tvResult.setText(R.string.blood_pressure_level_high_1)
                binding.vSign.setBackgroundResource(R.color.colorHighLevel1)
            }
            BloodPressureLevel.HIGH_LEVEL_2 -> {
                binding.tvResult.setText(R.string.blood_pressure_level_high_2)
                binding.vSign.setBackgroundResource(R.color.colorHighLevel2)
            }
            BloodPressureLevel.HIGH_LEVEL_3 -> {
                binding.tvResult.setText(R.string.blood_pressure_level_high_3)
                binding.vSign.setBackgroundResource(R.color.colorHighLevel3)
            }
        }
    }

    private fun appendResult(bloodPressureLevel: BloodPressureLevel, resultBuilder: StringBuilder) {
        when (bloodPressureLevel) {
            BloodPressureLevel.LOW ->
                resultBuilder.append(
                    binding.tvResult.context.getString(R.string.blood_pressure_level_low)
                )
            BloodPressureLevel.NORMAL_LOW ->
                resultBuilder.append(
                    binding.tvResult.context.getString(R.string.blood_pressure_level_normal_low)
                )
            BloodPressureLevel.NORMAL ->
                resultBuilder.append(
                    binding.tvResult.context.getString(R.string.blood_pressure_level_normal)
                )
            BloodPressureLevel.NORMAL_HIGH ->
                resultBuilder.append(
                    binding.tvResult.context.getString(R.string.blood_pressure_level_normal_high)
                )
            BloodPressureLevel.HIGH_LEVEL_1 ->
                resultBuilder.append(
                    binding.tvResult.context.getString(R.string.blood_pressure_level_high_1)
                )
            BloodPressureLevel.HIGH_LEVEL_2 ->
                resultBuilder.append(
                    binding.tvResult.context.getString(R.string.blood_pressure_level_high_2)
                )
            BloodPressureLevel.HIGH_LEVEL_3 ->
                resultBuilder.append(
                    binding.tvResult.context.getString(R.string.blood_pressure_level_high_3)
                )
        }
    }
}