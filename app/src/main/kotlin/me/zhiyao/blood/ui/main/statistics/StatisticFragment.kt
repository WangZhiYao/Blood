package me.zhiyao.blood.ui.main.statistics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.github.aachartmodel.aainfographics.aachartcreator.*
import dagger.hilt.android.AndroidEntryPoint
import me.zhiyao.blood.R
import me.zhiyao.blood.constants.BloodPressureType
import me.zhiyao.blood.data.model.StatisticItem
import me.zhiyao.blood.data.model.StatisticResult
import me.zhiyao.blood.databinding.FragmentStatisticBinding
import me.zhiyao.blood.ui.base.BaseFragment
import me.zhiyao.blood.util.TimeUtils
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
        get7DayAverage()
    }

    private fun get7DayAverage() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -7)
        viewModel.getDailyAverageStatisticResult(calendar.timeInMillis)
            .observe(viewLifecycleOwner, { statisticResult ->
                statisticResult.statisticItem.isNotEmpty().run {
                    binding.aaChartView.aa_drawChartWithChartModel(
                        configureDailyAverageChart(
                            if (this) statisticResult else makeExampleDailyAverageChartData(),
                            !this
                        )
                    )
                }
            })
    }

    private fun configureDailyAverageChart(
        statisticResult: StatisticResult,
        example: Boolean
    ): AAChartModel {
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

        val title =
            if (example) getString(R.string.statistic_chart_title_example)
            else getString(R.string.statistic_chart_title)
        val dateList = statisticResult.dateList
        val subtitle = "${dateList[0]} - ${dateList[dateList.size - 1]}"

        return AAChartModel()
            .chartType(AAChartType.Spline) //图形类型
            .animationType(AAChartAnimationType.Linear) //图形渲染动画类型
            .title(title) //图形标题
            .subtitle(subtitle) //图形副标题
            .dataLabelsEnabled(false) //是否显示数字
            .categories(statisticResult.dateList.toTypedArray())
            .markerSymbolStyle(AAChartSymbolStyleType.BorderBlank) //折线连接点样式
            .markerSymbol(AAChartSymbolType.Circle)
            .markerRadius(5f) //折线连接点半径长度,为0时相当于没有折线连接点
            .series(dataList.toTypedArray())
    }

    private fun makeExampleDailyAverageChartData(): StatisticResult {
        val dateList = ArrayList<String>()
        val dataList = ArrayList<StatisticItem>()

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -7)

        val r = Random()
        while (dateList.size < 7) {
            dateList.add(TimeUtils.timestamp2Str(TimeUtils.YYYY_MM_DD, calendar.timeInMillis))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val sysList = ArrayList<Int>()
        val diaList = ArrayList<Int>()
        val pulList = ArrayList<Int>()

        for (i in 0 until 7) {
            sysList.add(r.nextInt(100) + 100)
            diaList.add(r.nextInt(50) + 50)
            pulList.add(r.nextInt(40) + 60)
        }

        dataList.add(StatisticItem(BloodPressureType.SYS, sysList))
        dataList.add(StatisticItem(BloodPressureType.DIA, diaList))
        dataList.add(StatisticItem(BloodPressureType.PUL, pulList))

        return StatisticResult(dateList, dataList)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}