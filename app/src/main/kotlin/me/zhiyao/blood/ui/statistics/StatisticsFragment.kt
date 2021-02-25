package me.zhiyao.blood.ui.statistics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import me.zhiyao.blood.R
import me.zhiyao.blood.databinding.FragmentStatisticsBinding
import me.zhiyao.blood.ui.base.BaseFragment

class StatisticsFragment : BaseFragment(R.layout.fragment_statistics) {

    private val viewModel by viewModels<StatisticsViewModel>()

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStatisticsBinding.bind(view)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}