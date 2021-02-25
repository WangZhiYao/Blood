package me.zhiyao.blood.ui.record

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import me.zhiyao.blood.R
import me.zhiyao.blood.databinding.FragmentRecordBinding
import me.zhiyao.blood.ui.base.BaseFragment

class RecordFragment : BaseFragment(R.layout.fragment_record) {

    private val viewModel by viewModels<RecordViewModel>()

    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecordBinding.bind(view)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}