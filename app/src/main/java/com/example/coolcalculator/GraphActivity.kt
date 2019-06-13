package com.example.coolcalculator


import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_graph.*

class GraphActivity : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonPlus.setOnClickListener {
            if (ch<=3) {
                buttonPlus.setBackgroundResource(R.color.actionButton)
                val intent = Intent(requireActivity(), EnterGraphActivity::class.java)
                startActivityForResult(intent, 1)
            } else {
                buttonPlus.setBackgroundResource(R.color.backgroundMain)
            }
        }

        Build.setOnClickListener {
            val intent1 = Intent(requireActivity(), build::class.java)

            startActivityForResult(intent1, 1)
        }



    }

    override fun onResume() {
        super.onResume()
        if(getActivity() != null) {
            getActivity()?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    var ch = 1

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var data1 = data
        if ((data1 != null)&&(ch==1)) {
            tvY1.text = "1) y = ${data1.getStringExtra("name")}"
            tvY1.setTextColor(Color.WHITE)
            tvDelete1.setBackgroundResource(R.color.actionButton)
            ch++
            data1 = null
        }
        if ((data1 != null)&&(ch==2)) {
            tvY2.text = "2) y = ${data1.getStringExtra("name")}"
            tvY2.setTextColor(Color.WHITE)
            tvDelete2.setBackgroundResource(R.color.actionButton)
            ch++
            data1 = null
        }
        if ((data1 != null)&&(ch==3)) {
            tvY3.text = "3) y = ${data1.getStringExtra("name")}"
            tvY3.setTextColor(Color.WHITE)
            tvDelete3.setBackgroundResource(R.color.actionButton)
            ch++
            data1 = null
        }
    }
}

