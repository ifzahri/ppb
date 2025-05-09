package ppb.iftala.ets.mymoneynotes.screens

import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

@Composable
fun TransactionChartScreen(
    totalIncome: Double,
    totalExpense: Double
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        factory = { context ->
            PieChart(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                description.isEnabled = false
                isDrawHoleEnabled = true
                setHoleColor(Color.WHITE)
                setTransparentCircleColor(Color.WHITE)
                setTransparentCircleAlpha(110)
                holeRadius = 58f
                transparentCircleRadius = 61f
                setDrawCenterText(true)
                centerText = "Distribusi Keuangan"
                isRotationEnabled = false
                isHighlightPerTapEnabled = true
                legend.isEnabled = true

                setUsePercentValues(true)
            }
        },
        update = { pieChart ->
            val entries = ArrayList<PieEntry>().apply {
                if (totalIncome > 0) add(PieEntry(totalIncome.toFloat(), "Pemasukan"))
                if (totalExpense > 0) add(PieEntry(totalExpense.toFloat(), "Pengeluaran"))
            }

            val dataSet = PieDataSet(entries, "Transaksi Keuangan").apply {
                colors = listOf(Color.rgb(76, 175, 80), Color.rgb(229, 57, 53))
                valueTextSize = 14f
                valueTextColor = Color.WHITE
                sliceSpace = 3f
                selectionShift = 5f
            }

            val data = PieData(dataSet).apply {
                setValueFormatter(PercentFormatter())
                setValueTextSize(14f)
                setValueTextColor(Color.WHITE)
            }

            pieChart.data = data
            pieChart.invalidate()
        }
    )
}