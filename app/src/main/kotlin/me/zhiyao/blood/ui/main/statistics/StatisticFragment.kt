package me.zhiyao.blood.ui.main.statistics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.github.aachartmodel.aainfographics.aachartcreator.*
import dagger.hilt.android.AndroidEntryPoint
import me.zhiyao.blood.R
import me.zhiyao.blood.constants.BloodPressureType
import me.zhiyao.blood.data.model.StatisticResult
import me.zhiyao.blood.databinding.FragmentStatisticBinding
import me.zhiyao.blood.ui.base.BaseFragment
import java.util.*
import kotlin.collections.ArrayList


/**
 *
 * @author WangZhiYao
 * @date 2021/2/24
 */
@AndroidEntryPoint
class StatisticFragment : BaseFragment(R.layout.fragment_statistic) {

    private val viewModel by viewModels<StatisticViewModel>()

    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStatisticBinding.bind(view)

        initData()
    }

    private fun initData() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -7)
        viewModel.getDailyStatisticResult(calendar.timeInMillis).observe(viewLifecycleOwner, {
            if (it.statisticItem.isNotEmpty()) {
                binding.aaChartView.aa_drawChartWithChartModel(configureDailyChart(it))
            }
        })
    }

    private fun configureDailyChart(statisticResult: StatisticResult): AAChartModel {
        val dataList = ArrayList<AASeriesElement>()
        statisticResult.statisticItem.forEach {
            dataList.add(
                AASeriesElement()
                    .name(
                        when (it.type) {
                            BloodPressureType.SYS -> getString(R.string.blood_pressure_sys)
                            BloodPressureType.DIA -> getString(R.string.blood_pressure_dia)
                            BloodPressureType.PUL -> getString(R.string.blood_pressure_pul)
                        }
                    )
                    .data(it.data.toTypedArray())
            )
        }

        return AAChartModel()
            .chartType(AAChartType.Spline) //图形类型
            .animationType(AAChartAnimationType.Linear) //图形渲染动画类型为"bounce"
            .title(getString(R.string.statistic_chart_title)) //图形标题
            .subtitle("${statisticResult.dateList[0]} - ${statisticResult.dateList[6]}") //图形副标题
            .dataLabelsEnabled(false) //是否显示数字
            .categories(statisticResult.dateList.toTypedArray())
            .markerSymbolStyle(AAChartSymbolStyleType.BorderBlank) //折线连接点样式
            .markerSymbol(AAChartSymbolType.Circle)
            .markerRadius(5f) //折线连接点半径长度,为0时相当于没有折线连接点
            .series(dataList.toTypedArray())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}