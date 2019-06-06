package com.example.coolcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_engi_calc.*
import net.objecthunter.exp4j.ExpressionBuilder

class EngiCalcActivity : AppCompatActivity() {

    var isException : Boolean = false //Есть сейчас в TextView ошибка или нет.
    var isDbl : Boolean = false // Вводится ли сейчас дробная часть числа или нет

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_engi_calc)

        getSupportActionBar()!!.hide()

        tvOne.setOnClickListener {appendOnExpression("1",true)}
        tvTwo.setOnClickListener {appendOnExpression("2",true)}
        tvThree.setOnClickListener {appendOnExpression("3",true)}
        tvFour.setOnClickListener {appendOnExpression("4",true)}
        tvFive.setOnClickListener {appendOnExpression("5",true)}
        tvSix.setOnClickListener {appendOnExpression("6",true)}
        tvSeven.setOnClickListener {appendOnExpression("7",true)}
        tvEight.setOnClickListener {appendOnExpression("8",true)}
        tvNine.setOnClickListener {appendOnExpression("9",true)}
        tvDot.setOnClickListener {appendOnExpression(".",true)}
        tvZero.setOnClickListener {appendOnExpression("0",true)}

        tvPlus.setOnClickListener {appendOnExpression("+", false)}
        tvMinus.setOnClickListener {appendOnExpression("-", false)}
        tvMul.setOnClickListener {appendOnExpression("*", false)}
        tvDevide.setOnClickListener {appendOnExpression("/", false)}
        tvOpen.setOnClickListener {appendOnExpression("(", false)}
        tvClose.setOnClickListener {appendOnExpression(")", false)}

        tvClear.setOnClickListener{
            tvExpression.text = ""
            tvResult.text = ""
            isException = false
        }

        tvBack.setOnClickListener{
            if(isException){
                tvExpression.text = ""
                isException = false
            }
            val string = tvExpression.text.toString()
            if(string.isNotEmpty()){
                tvExpression.text = string.substring(0, string.length-1)
            }
            tvResult.text = ""
        }

        tvEquals.setOnClickListener{
            try {

                val expression = ExpressionBuilder(tvExpression.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if(result == longResult.toDouble())
                    tvResult.text = longResult.toString()
                else
                    tvResult.text = result.toString()

            }catch (e:Exception){
                if(isException){
                    tvExpression.text = ""
                    tvResult.text = ""
                    isException = false
                } else{
                    tvExpression.text = "Error"
                    Log.d("Exception", "message: " + e.message)
                    isException = true
                }
            }
        }

    }

    fun isOper(string : String) : Boolean{
        return (string == "*" || string == "+" || string == "-" || string == "/")
    }

    fun appendOnExpression(string : String, canClear : Boolean) {
        val n : Int = tvExpression.text.length

        if(isException)
            return

        if(n == 0 && isOper(string))
            return

        if(isDbl && string == ".")
            return

        if(!isDbl && n > 0 && tvExpression.text[n - 1] == '0'
            && ((n >= 2 && !(tvExpression.text[n - 2] in '0'..'9')) || (n == 1)) && string in "0".."9")
            return

        if(string == "." && (n == 0 || !(tvExpression.text[n - 1] in '0'..'9')))
            return

        if(n > 0 && isOper(tvExpression.text[n - 1].toString()) && isOper(string))
            tvExpression.text = tvExpression.text.substring(0, n - 1)
        else if(n > 0 && tvExpression.text[n - 1] == '.' && !(string in "0".."9"))
            tvExpression.text = tvExpression.text.substring(0, n - 1)


        if(tvResult.text.isNotEmpty()){
            tvExpression.text = ""
        }

        if(string == ".") isDbl = true
        else if(!(string in "0".."9")) isDbl = false

        if(canClear) {
            tvResult.text = ""
            tvExpression.append(string)
        }else {
            tvExpression.append(tvResult.text)
            tvExpression.append(string)
            tvResult.text = ""
        }
    }

}