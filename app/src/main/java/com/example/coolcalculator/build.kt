package com.example.coolcalculator

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_build.*

class build: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build)

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        var data : List<Pair<Int, Int>> = List(10) { i -> Pair(i, i * i)}
        var entries : List<Entry> = emptyList()
        for(i in data)
            entries = entries.plus(Entry(i.first.toFloat(), i.second.toFloat()))
        var dataSet : LineDataSet = LineDataSet(entries, "TestObj")

        var lineData : LineData = LineData(dataSet)
        chart.setData(lineData)
        chart.invalidate()
    }

}