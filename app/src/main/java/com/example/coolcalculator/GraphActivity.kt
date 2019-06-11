package com.example.coolcalculator

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_graph.*

class   GraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        getSupportActionBar()!!.hide()

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        var data : List<Pair<Int, Int>> = List(21) { i -> Pair(i - 10, (i - 10) * (i - 10))}
        var data2 : List<Pair<Int, Int>> = List(21) { i -> Pair(i - 10, -(i - 10) * (i - 10))}
        var entries1 : List<Entry> = emptyList()
        var entries2 : List<Entry> = emptyList()
        for(i in data) {
            entries1 = entries1.plus(Entry(i.first.toFloat(), i.second.toFloat()))
        }
        for(i in data2)
            entries2 = entries2.plus(Entry(i.first.toFloat(), i.second.toFloat()))
        var dataSet1 : LineDataSet = LineDataSet(entries1, "TestObject 1")
        var dataSet2 : LineDataSet = LineDataSet(entries2, "TestObject 2")
        var dataSets : List<LineDataSet> = ArrayList()
        dataSets = dataSets.plus(dataSet1)
        dataSets = dataSets.plus(dataSet2)

        var lineData : LineData = LineData(dataSets)
    }

}
