package com.example.coolcalculator

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCalc.setOnClickListener {
            val intent = Intent(this, EngiCalcActivity::class.java)

            startActivity(intent)
        }

        buttonGraph.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)

            startActivity(intent)
        }
    }

}
