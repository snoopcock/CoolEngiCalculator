package com.example.coolcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_engi_calc.*
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.function.Function
import net.objecthunter.exp4j.operator.Operator



class EngiCalcActivity : AppCompatActivity() {

    var isException : Boolean = false //Есть сейчас в TextView ошибка или нет.
    var isDbl : Boolean = false // Вводится ли сейчас дробная часть числа или нет

    fun setTvButtons(){
        tvOne.setOnClickListener {appendOnExpression("1",true)}
        tvTwo.setOnClickListener {appendOnExpression("2",true)}
        tvThree.setOnClickListener {appendOnExpression("3",true)}
        tvFour.setOnClickListener {appendOnExpression("4",true)}
        tvFive.setOnClickListener {appendOnExpression("5",true)}
        tvSix.setOnClickListener {appendOnExpression("6",true)}
        tvSeven.setOnClickListener {appendOnExpression("7",true)}
        tvEight.setOnClickListener {appendOnExpression("8",true)}
        tvNine.setOnClickListener {appendOnExpression("9",true)}
        tvZero.setOnClickListener {appendOnExpression("0",true)}

        tvDot.setOnClickListener {appendOnExpression(".",false)}
        tvPlus.setOnClickListener {appendOnExpression("+", false)}
        tvMinus.setOnClickListener {appendOnExpression("-", false)}
        tvMul.setOnClickListener {appendOnExpression("*", false)}
        tvDevide.setOnClickListener {appendOnExpression("/", false)}
        tvOpen.setOnClickListener {appendOnExpression("(", false)}
        tvClose.setOnClickListener {appendOnExpression(")", false)}
        if(tvFact != null)
            tvFact.setOnClickListener { appendOnExpression("!", false) }
        if(tvPow != null)
            tvPow.setOnClickListener{appendOnExpression("^", false)}
        if(tvComma != null)
            tvComma.setOnClickListener { appendOnExpression(",", false) }
        if(tvSqrtXY != null)
            tvSqrtXY.setOnClickListener { appendOnExpression("sqrt(", true) }
        if(tvLog != null)
            tvLog.setOnClickListener { appendOnExpression("log(", true) }
        if(tvArcSin != null)
            tvArcSin.setOnClickListener { appendOnExpression("asin(", true) }
        if(tvArcCos != null)
            tvArcCos.setOnClickListener { appendOnExpression("acos(", true) }
        if(tvArcTg != null)
            tvArcTg.setOnClickListener { appendOnExpression("atan(", true) }
        if(tvTg != null)
            tvTg.setOnClickListener { appendOnExpression("tan(", true) }
        if(tvCtg != null)
            tvCtg.setOnClickListener { appendOnExpression("cot(", true) }
        if(tvArcСtg != null)
            tvArcСtg.setOnClickListener { appendOnExpression("acot(", true) }
        if(tvSin != null)
            tvSin.setOnClickListener { appendOnExpression("sin(", true) }
        if(tvCos != null)
            tvCos.setOnClickListener { appendOnExpression("cos(", true) }
        if(tvPi != null)
            tvPi.setOnClickListener{appendOnExpression("pi", true)}
        if(tvPer != null)
            tvPer.setOnClickListener {
                if(isException) return@setOnClickListener
                var n : Int = tvExpression.text.length
                if(n == 0) return@setOnClickListener
                if(tvExpression.text[n - 1] == '.' || tvExpression.text[n - 1] == '(') return@setOnClickListener
                if(isOper(tvExpression.text[n - 1].toString())) tvExpression.text = tvExpression.text.substring(0, n - 1)
                tvExpression.text = "0.01(" + tvExpression.text + ")"
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.run {
            putString("EXP", tvExpression.text.toString())
            putString("RES", tvResult.text.toString())
            putBoolean("EXC", isException)
            putBoolean("DB", isDbl)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setTvButtons()

        tvExpression.text = savedInstanceState.getString("EXP")
        tvResult.text = savedInstanceState.getString("RES")
        isException = savedInstanceState.getBoolean("EXC")
        isDbl = savedInstanceState.getBoolean("DB")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_engi_calc)

        getSupportActionBar()!!.hide()

        Log.d("MainChecker: ", "Everything fine in EngiCalcActivity")

        setTvButtons()

        tvClear.setOnClickListener{
            tvExpression.text = ""
            tvResult.text = ""
            isException = false
            isDbl = false
        }

        tvBack.setOnClickListener{
            if(isException){
                tvExpression.text = ""
                isException = false
            }
            val n : Int = tvExpression.text.length
            if(n > 1 && tvExpression.text[n - 1] == '(' && tvExpression.text[n - 2] in 'a'..'z' && tvExpression.text[n - 2] != 'i'){
                tvExpression.text = tvExpression.text.substring(0, tvExpression.text.length - 1)
                while (tvExpression.text.length > 0 && tvExpression.text[tvExpression.text.length - 1] in 'a'..'z')
                    tvExpression.text = tvExpression.text.substring(0, tvExpression.text.length - 1)
            }
            else if(n > 1 && tvExpression.text[n - 1] == 'i'){
                tvExpression.text = tvExpression.text.substring(0, tvExpression.text.length - 2)
            }
            else if(tvExpression.text.isNotEmpty()){
                tvExpression.text = tvExpression.text.substring(0, tvExpression.text.length-1)
            }
            isDbl = false
            for(i in (tvExpression.text.length - 1) downTo 0){
                if(tvExpression.text[i] == '.'){
                    isDbl = true
                    break
                } else if(!(tvExpression.text[i] in '0'..'9')) break;
            }
            tvResult.text = ""
        }

        tvEquals.setOnClickListener{
            if(tvExpression.text.isEmpty()) return@setOnClickListener
            try {
                val expression = ExpressionBuilder(tvExpression.text.toString()).function(sqrt).function(log).function(cot).function(acot).operator(factorial).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if(result == longResult.toDouble())
                    tvResult.text = longResult.toString()
                else
                    tvResult.text = result.toString()
                if(tvResult.text == "NaN"){
                    tvResult.text = ""
                    throw IllegalArgumentException("Bad Argument")
                }

            } catch (e:Exception){
                if(isException){
                    tvExpression.text = ""
                    tvResult.text = ""
                    isException = false
                } else{
                    tvExpression.text = "Error"
                    Log.d("Exception", "message: " + e.message)
                    isException = true
                }
                isDbl = false
            }
        }

    }
    //--------------------------------------------------------------------------------------------------
    //************************Additional functions and operators****************************************
    //--------------------------------------------------------------------------------------------------

    var sqrt = object: Function("sqrt", 2){
        override fun apply(vararg args : Double) : Double{
            when(args[1]){
                2.0 -> return Math.sqrt(args[0])
                3.0 -> return Math.cbrt(args[0])
                else -> {
                    if(args[0] < 0 && args[1].toInt() % 2 == 1) throw IllegalArgumentException("Bad Argument")
                    return Math.pow(args[0], 1 / args[1])
                }
            }
        }
    }

    var log = object : Function("log", 2) {
        override fun apply(vararg args: Double): Double {
            return Math.log(args[0]) / Math.log(args[1])
        }
    }

    var cot = object : Function("cot",  1){
        override fun apply(vararg args : Double) : Double {
            return 1 / Math.tan(args[0] * Math.PI / 180)
        }
    }

    var acot = object : Function("acot", 1){
        override fun apply(vararg args : Double) : Double{
            return Math.atan(-args[0]) + Math.PI / 2
        }
    }

    var factorial: Operator = object : Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

        override fun apply(vararg args: Double): Double {
            val arg = args[0].toInt()
            if (arg.toDouble() != args[0]) {
                throw IllegalArgumentException("Operand for factorial has to be an integer")
            }
            if (arg < 0) {
                throw IllegalArgumentException("The operand of the factorial can not be less than zero")
            }
            var result = 1.0
            for (i in 1..arg) {
                result *= i.toDouble()
            }
            return result
        }
    }

    //--------------------------------------------------------------------------------------------------
    //**************************************************************************************************
    //--------------------------------------------------------------------------------------------------


    fun isOper(string : String) : Boolean{
        return (string == "*" || string == "+" || string == "-" || string == "/" || string == "^" || string == "!")
    }

    fun appendOnExpression(string : String, canClear : Boolean) {
        val n : Int = tvExpression.text.length

        if(isException)
            return

        if(tvResult.text.isNotEmpty()){
            tvExpression.text = ""
            isDbl = false
            if(canClear) {
                tvResult.text = ""
                tvExpression.append(string)
            }else {
                tvExpression.append(tvResult.text)
                isDbl = false
                for(i in (tvResult.text.length - 1) downTo 0){
                    if(tvResult.text[i] == '.'){
                        isDbl = true
                        break
                    } else if(!(tvResult.text[i] in '0'..'9')) break;
                }
                if(string != "." || !isDbl)
                    tvExpression.append(string)
                tvResult.text = ""
            }
            return
        }

        if(n == 0 && string != "-" && isOper(string))
            return

        if(n > 0 && tvExpression.text[n - 1] == '(' && string != "-" && isOper(string))
            return

        if(string == ","){
            if(n == 0) return
            if(tvExpression.text[n - 1] == '.' || tvExpression.text[n - 1] == '(' || isOper(tvExpression.text[n - 1].toString())) return
            var ind : Int = -1
            var bal : Int = 0
            var cnt : Int = 0
            for(i in (n - 1) downTo 0){
                if(tvExpression.text[i] == '(' && bal == 0){
                    ind = i
                    break
                }
                if(tvExpression.text[i] == ',' && bal == 0){
                    cnt++
                }
                if(tvExpression.text[i] == ')')
                    bal--
                if(tvExpression.text[i] == '(')
                    bal++
            }
            if(ind <= 0 || !(tvExpression.text[ind - 1] in 'a'..'z') || tvExpression.text[ind - 1] == 'i') return
            var arg : String = ""
            ind--
            while(ind >= 0 && tvExpression.text[ind] in 'a'..'z'){
                arg = tvExpression.text[ind] + arg
                ind--
            }
            if(cnt >= 1) return
            when(arg){
                "asin", "acos", "atan", "tan", "cot", "acot", "sin", "cos" -> return
                else -> {}
            }
        }

        if(n > 0 && string in "0".."9" && tvExpression.text[n - 1] == 'i'){
            tvExpression.append("*")
        }

        if(isDbl && string == ".")
            return

        if(!isDbl && n > 0 && tvExpression.text[n - 1] == '0'
            && ((n >= 2 && !(tvExpression.text[n - 2] in '0'..'9')) || (n == 1)) && string in "0".."9")
            return

        if(string == "." && (n == 0 || !(tvExpression.text[n - 1] in '0'..'9')))
            return

        if(n > 0 && tvExpression.text[n - 1] != '!' && isOper(tvExpression.text[n - 1].toString()) && isOper(string)) {
            if(n == 1) return
            tvExpression.text = tvExpression.text.substring(0, n - 1)
        }
        else if(n > 0 && tvExpression.text[n - 1] == '.' && !(string in "0".."9")) {
            if (n == 1) return
            tvExpression.text = tvExpression.text.substring(0, n - 1)
        }

        if(n > 0 && string.length > 1 && tvExpression.text[n - 1] == 'i') tvExpression.append("*")

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