package com.example.coolcalculator


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
            val intent = Intent(requireActivity(), EnterGraphActivity::class.java)
            startActivityForResult(intent, 1)


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        print(data)
    }
}

