package me.zhiyao.blood.ui.record

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import me.zhiyao.blood.R
import me.zhiyao.blood.data.db.model.BloodPressure
import me.zhiyao.blood.databinding.ActivityNewRecordBinding
import me.zhiyao.blood.ext.showToast
import me.zhiyao.blood.ext.viewBinding
import me.zhiyao.blood.ui.base.BaseActivity
import me.zhiyao.blood.ui.record.widget.DatePickerFragment
import me.zhiyao.blood.ui.record.widget.TimePickerFragment
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *
 * @author WangZhiYao
 * @date 2021/2/26
 */
@AndroidEntryPoint
class NewRecordActivity : BaseActivity() {

    companion object {
        private const val TAG_DATE_PICKER = "DATE_PICKER"

        private const val TAG_TIME_PICKER = "TIME_PICKER"
    }

    private val binding by viewBinding(ActivityNewRecordBinding::inflate)
    private val viewModel by viewModels<NewRecordViewModel>()

    private lateinit var datePickerFragment: DatePickerFragment
    private lateinit var timePickerFragment: TimePickerFragment

    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    override fun canGoBack() = true

    private fun initView() {
        binding.actvMeasureDate.setText(
            getString(
                R.string.new_record_measure_date,
                calendar.get(Calendar.YEAR).toString(),
                calendar.get(Calendar.MONTH).toString(),
                calendar.get(Calendar.DAY_OF_MONTH).toString()
            )
        )

        binding.actvMeasureDate.setOnClickListener {
            datePickerFragment = DatePickerFragment(calendar)
            datePickerFragment.setOnDateSetListener { year, month, dayOfMonth ->
                binding.actvMeasureDate.setText(
                    getString(
                        R.string.new_record_measure_date,
                        year.toString(),
                        month.toString(),
                        dayOfMonth.toString()
                    )
                )
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }

            datePickerFragment.show(supportFragmentManager, TAG_DATE_PICKER)
        }

        binding.actvMeasureTime.setText(
            getString(
                R.string.new_record_measure_time,
                calendar.get(Calendar.HOUR_OF_DAY).toString(),
                calendar.get(Calendar.MINUTE).toString()
            )
        )

        binding.actvMeasureTime.setOnClickListener {
            timePickerFragment = TimePickerFragment(calendar)
            timePickerFragment.setOnTimeSetListener { hourOfDay, minute ->
                binding.actvMeasureTime.setText(
                    getString(
                        R.string.new_record_measure_time,
                        hourOfDay.toString(),
                        minute.toString()
                    )
                )
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
            }

            timePickerFragment.show(supportFragmentManager, TAG_TIME_PICKER)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_new_record, menu)
        menu?.findItem(R.id.action_complete)?.let {
            it.clicks()
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe {
                    attemptInsertBloodPressureRecord()
                }
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun attemptInsertBloodPressureRecord() {
        val sys = binding.tietSys.text.toString()
        val dia = binding.tietDia.text.toString()
        val pul = binding.tietPul.text.toString()

        if (sys.isBlank() || dia.isBlank() || pul.isBlank()) {
            showToast(R.string.new_record_params_required)
            return
        }

        val bloodPressure = BloodPressure(
            sys.toInt(), dia.toInt(), pul.toInt(),
            calendar.timeInMillis, System.currentTimeMillis()
        )

        viewModel.insertBloodPressure(bloodPressure)
        finish()
    }

    override fun onDestroy() {
        if (this::datePickerFragment.isInitialized) {
            datePickerFragment.dismiss()
        }

        if (this::timePickerFragment.isInitialized) {
            timePickerFragment.dismiss()
        }

        super.onDestroy()
    }
}