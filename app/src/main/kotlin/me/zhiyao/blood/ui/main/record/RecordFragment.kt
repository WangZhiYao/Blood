package me.zhiyao.blood.ui.main.record

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.zhiyao.blood.R
import me.zhiyao.blood.data.db.model.BloodPressure
import me.zhiyao.blood.databinding.FragmentRecordBinding
import me.zhiyao.blood.ext.showSnackBar
import me.zhiyao.blood.ui.base.BaseFragment
import me.zhiyao.blood.ui.main.record.adapter.RecordAdapter
import me.zhiyao.blood.ui.main.record.listener.OnRecordClickListener

/**
 *
 * @author WangZhiYao
 * @date 2021/2/24
 */
@AndroidEntryPoint
class RecordFragment : BaseFragment(R.layout.fragment_record), OnRecordClickListener {

    private val viewModel by viewModels<RecordViewModel>()

    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!

    private lateinit var recordAdapter: RecordAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecordBinding.bind(view)

        initView()
        initData()
    }

    private fun initView() {
        recordAdapter = RecordAdapter(this)
        recordAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    binding.rvBloodPressureRecord.smoothScrollToPosition(0)
                }
            }
        })

        binding.rvBloodPressureRecord.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBloodPressureRecord.adapter = recordAdapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) = makeMovementFlags(0, ItemTouchHelper.START)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                recordAdapter.peek(viewHolder.absoluteAdapterPosition)?.run {
                    viewModel.deleteRecord(this)
                    binding.rvBloodPressureRecord.showSnackBar(
                        R.string.record_delete_message,
                        Snackbar.LENGTH_LONG,
                        R.string.record_delete_revoke
                    ) {
                        viewModel.insertRecord(this)
                    }
                }
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.rvBloodPressureRecord)
    }

    private fun initData() {
        viewModel.bloodPressureRecordList.observe(viewLifecycleOwner, {
            viewLifecycleOwner.lifecycleScope.launch {
                recordAdapter.submitData(it)
            }
        })
    }

    override fun onRecordClicked(bloodPressure: BloodPressure) {

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}