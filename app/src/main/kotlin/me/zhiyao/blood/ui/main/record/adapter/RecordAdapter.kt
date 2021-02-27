package me.zhiyao.blood.ui.main.record.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import me.zhiyao.blood.data.db.model.BloodPressure
import me.zhiyao.blood.databinding.ItemBloodPressureBinding
import me.zhiyao.blood.ext.viewBinding
import me.zhiyao.blood.ui.main.record.listener.OnRecordClickListener
import me.zhiyao.blood.ui.main.record.viewholder.RecordViewHolder

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
class RecordAdapter(
    private val onRecordClickListener: OnRecordClickListener
) : PagingDataAdapter<BloodPressure, RecordViewHolder>(BloodPressureComparator) {

    object BloodPressureComparator : DiffUtil.ItemCallback<BloodPressure>() {
        override fun areItemsTheSame(oldItem: BloodPressure, newItem: BloodPressure) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: BloodPressure, newItem: BloodPressure) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecordViewHolder(parent.viewBinding(ItemBloodPressureBinding::inflate))

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        getItem(position)?.let { bloodPressure ->
            holder.bind(bloodPressure)
            holder.itemView.setOnClickListener {
                onRecordClickListener.onRecordClicked(bloodPressure)
            }
        }
    }
}