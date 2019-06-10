package com.example.coolcalculator

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()!!.hide()

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        buttonCalc.setOnClickListener {
            val intent = Intent(this, EngiCalcActivity::class.java)
            Log.d("MainActivity: ", "Everything fine in MainActivity")
            startActivity(intent)
        }

        buttonGraph.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)

            startActivity(intent)
        }
    }
}
