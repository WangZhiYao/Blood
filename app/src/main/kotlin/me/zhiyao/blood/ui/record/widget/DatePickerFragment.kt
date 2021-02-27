package me.zhiyao.blood.ui.record.widget

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

/**
 *
 * @author WangZhiYao
 * @date 2021/2/27
 */
class DatePickerFragment(private val calendar: Calendar = Calendar.getInstance()) :
    DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var onDateSetListener: ((year: Int, month: Int, dayOfMonth: Int) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            this,
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis

        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        onDateSetListener?.invoke(year, month, dayOfMonth)
    }

    fun setOnDateSetListener(onDateSetListener: (year: Int, month: Int, dayOfMonth: Int) -> Unit) {
        this.onDateSetListener = onDateSetListener
    }
}