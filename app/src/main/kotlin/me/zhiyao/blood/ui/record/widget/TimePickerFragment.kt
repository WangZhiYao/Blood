package me.zhiyao.blood.ui.record.widget

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

/**
 *
 * @author WangZhiYao
 * @date 2021/2/27
 */
class TimePickerFragment(private val calendar: Calendar = Calendar.getInstance()) :
    DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var onTimeSetListener: ((hourOfDay: Int, minute: Int) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(
            requireContext(),
            this,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        onTimeSetListener?.invoke(hourOfDay, minute)
    }

    fun setOnTimeSetListener(onTimeSetListener: (hourOfDay: Int, minute: Int) -> Unit) {
        this.onTimeSetListener = onTimeSetListener
    }
}