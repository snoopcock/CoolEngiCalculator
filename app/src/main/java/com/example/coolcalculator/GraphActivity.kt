package com.example.coolcalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


import kotlinx.android.synthetic.main.activity_graph.*

class GraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        getSupportActionBar()!!.hide()

    }

}
