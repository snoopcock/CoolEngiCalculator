package com.example.coolcalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EnterGraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_graph)

        getSupportActionBar()!!.hide()

    }
}
