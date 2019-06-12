package com.example.coolcalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_engi_calc.*
import kotlinx.android.synthetic.main.activity_enter_graph.*
import android.R.id.edit
import android.content.Intent
import android.content.SharedPreferences.Editor
import android.preference.PreferenceManager




class EnterGraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_graph)

        getSupportActionBar()!!.hide()

        tvOne1.setOnClickListener {appendOnFunction("1",true)}
        tvTwo1.setOnClickListener {appendOnFunction("2",true)}
        tvThree1.setOnClickListener {appendOnFunction("3",true)}
        tvFour1.setOnClickListener {appendOnFunction("4",true)}
        tvFive1.setOnClickListener {appendOnFunction("5",true)}
        tvSix1.setOnClickListener {appendOnFunction("6",true)}
        tvSeven1.setOnClickListener {appendOnFunction("7",true)}
        tvEight1.setOnClickListener {appendOnFunction("8",true)}
        tvNine1.setOnClickListener {appendOnFunction("9",true)}
        tvDot1.setOnClickListener {appendOnFunction(".",true)}
        tvZero1.setOnClickListener {appendOnFunction("0",true)}

        tvPlus1.setOnClickListener {appendOnFunction("+", false)}
        tvMinus1.setOnClickListener {appendOnFunction("-", false)}
        tvMul1.setOnClickListener {appendOnFunction("*", false)}
        tvDevide1.setOnClickListener {appendOnFunction("/", false)}
        tvOpen1.setOnClickListener {appendOnFunction("(", false)}
        tvClose1.setOnClickListener {appendOnFunction(")", false)}
        tvX.setOnClickListener {appendOnFunction("x", false)}
        tvDegree.setOnClickListener {appendOnFunction("^",  false)}

        val s_pref = PreferenceManager.getDefaultSharedPreferences(this)
        val edit = s_pref.edit()
        tvEnter.setOnClickListener{
            edit.putString("textview_text","some text");
            edit.commit();

            val intent = Intent(this, GraphActivity::class.java)
            setResult(1, intent)

            //startActivity(intent)

        }

        tvBack1.setOnClickListener{
            val string = tvFunction.text.toString()
            if(string.isNotEmpty()){
                tvFunction.text = string.substring(0, string.length-1)
            }
        }

    }


    fun appendOnFunction(string : String, canClear : Boolean) {
        tvFunction.append(string)
    }
}