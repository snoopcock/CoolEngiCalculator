package com.example.coolcalculator

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_graph.*

class GraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        getSupportActionBar()!!.hide()

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        var ch : Int = 0

        buttonPlus.setOnClickListener {
            val intent = Intent(this, EnterGraphActivity::class.java)
            intent.putExtra("che", ch)
            startActivity(intent)

        }

        if ((intent.hasExtra("TAGgraph"))&&(intent.getIntExtra("che", -1)==1)) {
            var data = intent.getStringExtra("TAGgraph")
            tvY1.text = "1) y = $data"
            tvY1.setTextColor(Color.WHITE)
            tvShow1.setBackgroundResource(R.color.actionButton)
            tvDelete1.setBackgroundResource(R.color.actionButton)

        }

        if ((intent.hasExtra("TAGgraph"))&&(intent.getIntExtra("che", -1)==2)) {
            var data = intent.getStringExtra("TAGgraph")
            tvY2.text = "2) y = $data"
            tvY2.setTextColor(Color.WHITE)
            tvShow2.setBackgroundResource(R.color.actionButton)
            tvDelete2.setBackgroundResource(R.color.actionButton)
        }

        if ((intent.hasExtra("TAGgraph"))&&(intent.getIntExtra("che", -1)==3)) {
            var data = intent.getStringExtra("TAGgraph")
            tvY3.text = "3) y = $data"
            tvY3.setTextColor(Color.WHITE)
            tvShow3.setBackgroundResource(R.color.actionButton)
            tvDelete3.setBackgroundResource(R.color.actionButton)
        }



    }
}
