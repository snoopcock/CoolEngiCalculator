package com.example.coolcalculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_engi_calc.*
import net.objecthunter.exp4j.ExpressionBuilder
import net.objecthunter.exp4j.function.Function
import net.objecthunter.exp4j.operator.Operator





class EngiCalcActivity : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_engi_calc, container, false)
    }



    var isException : Boolean = false //Есть сейчас в TextView ошибка или нет.
    var isDbl : Boolean = false // Вводится ли сейчас дробная часть числа или нет
    var curBal : Int = 0 //Баланс скобок

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
            if (tvExpression != null) {
                putString("EXP", tvExpression.text.toString())
            }
            if (tvResult != null) {
                putString("RES", tvResult.text.toString())
            }
            if (curBal != null) {
                putInt("BL", curBal)
            }
            putBoolean("EXC", isException)
            putBoolean("DB", isDbl)

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        tvExpression.text = savedInstanceState?.getString("EXP")
        tvResult.text = savedInstanceState?.getString("RES")
        if (savedInstanceState?.getBoolean("EXC") != null) {
            isException = savedInstanceState?.getBoolean("EXC")
        }
        if (savedInstanceState?.getBoolean("DB") != null) {
            isDbl = savedInstanceState?.getBoolean("DB")
        }
        if (savedInstanceState?.getInt("BL") != null) {
            curBal = savedInstanceState?.getInt("BL")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d("EngiCalcActivity: ", "Everything fine in EngiCalcActivity")

        setTvButtons()

        tvClear.setOnClickListener{
            tvExpression.text = ""
            tvResult.text = ""
            isException = false
            isDbl = false
            curBal = 0
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
                curBal--
            }
            else if(n > 1 && tvExpression.text[n - 1] == 'i'){
                tvExpression.text = tvExpression.text.substring(0, tvExpression.text.length - 2)
            }
            else if(tvExpression.text.isNotEmpty()){
                if(tvExpression.text[n - 1] == '(') curBal--
                if(tvExpression.text[n - 1] == ')') curBal++
                tvExpression.text = tvExpression.text.substring(0, tvExpression.text.length-1)
            }
            isDbl = false
            for(i in (tvExpression.text.length - 1) downTo 0){
                if(tvExpression.text[i] == '.'){
                    isDbl = true
                    break
                } else if(!(tvExpression.text[i] in '0'..'9')) break;
            }
            reCalc()
            //tvResult.text = ""
        }

        tvEquals.setOnClickListener{
            if(tvExpression.text.isEmpty()) return@setOnClickListener
            try {
                var exp : String = tvExpression.text.toString()
                if(exp[exp.length - 1] == '*' || exp[exp.length - 1] == '+' || exp[exp.length - 1] == '-' || exp[exp.length - 1] == '/'){
                    exp = exp.substring(0, exp.length - 1)
                }
                if(exp.length == 0){
                    tvResult.text = ""
                    return@setOnClickListener
                }
                if(curBal > 0)
                    for(i in 1..curBal)
                        exp = exp.plus(')')
                for(i : Int in 0 until exp.length){
                    if(exp[i] != 'q') continue
                    var bal : Int = 0
                    var cnt : Int = 0
                    var ind : Int = -1
                    for(j : Int in (i + 4) until exp.length){
                        if(exp[j] == ')' && bal == 0){
                            ind = j
                            break
                        }
                        if(exp[j] == ',' && bal == 0) cnt++
                        if(exp[j] == '(') bal++
                        if(exp[j] == ')') bal--
                    }
                    if(ind == -1) break
                    if(cnt > 0) continue
                    exp = exp.substring(0, ind) + ",2" + exp.substring(ind, exp.length)
                }
                Log.d("EngiCalcActivity: ", "tvExpression: ${exp}")
                val expression = ExpressionBuilder(exp).function(sqrtXY).function(log).function(cot).function(acot).operator(factorial).build()
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
                tvExpression.text = tvResult.text
                isDbl = false
                for(i in (tvExpression.text.length - 1) downTo 0){
                    if(tvExpression.text[i] == '.'){
                        isDbl = true
                        break
                    } else if(!(tvExpression.text[i] in '0'..'9')) break;
                }
            } catch (e:Exception){
                if(isException){
                    tvExpression.text = ""
                    tvResult.text = ""
                    isException = false
                } else{
                    tvExpression.text = "Error"
                    tvResult.text = ""
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

    var sqrtXY = object: Function("sqrt", 2){
        override fun apply(vararg args : Double) : Double{
            Log.d("EngiCalcActivity: ", "SQRT CHECK 1")
            if(args[1] == 0.0) throw java.lang.IllegalArgumentException("Bad Argument")
            when(args[1]){
                2.0 -> return Math.sqrt(args[0])
                3.0 -> return Math.cbrt(args[0])
                else -> {
                    if(args[0] < 0 && args[1].toInt() % 2 == 0) throw IllegalArgumentException("Bad Argument")
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
            return 1 / Math.tan(args[0] )
        }
    }

    var tan = object : Function("tan",  1){
        override fun apply(vararg args : Double) : Double {
            return Math.tan(args[0])
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

        /*if(tvResult.text.isNotEmpty()){
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
        }*/

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
            if(tvExpression.text[n - 2] == '(') return
            tvExpression.text = tvExpression.text.substring(0, n - 1)
        }
        else if(n > 0 && tvExpression.text[n - 1] == '.' && !(string in "0".."9")) {
            if (n == 1) return
            if(tvExpression.text[n - 2] == '(') return
            tvExpression.text = tvExpression.text.substring(0, n - 1)
        }

        if(n > 0 && string.length > 1 && tvExpression.text[n - 1] == 'i') tvExpression.append("*")

        if(string == ".") isDbl = true
        else if(!(string in "0".."9")) isDbl = false

        if(canClear) {
            tvResult.text = ""
            tvExpression.append(string)
        }else {
            //tvExpression.append(tvResult.text)
            tvExpression.append(string)
            //tvResult.text = ""
        }

        if(string[string.length - 1] == '(') curBal++
        if(string == ")") curBal--

        reCalc()
    }
    fun reCalc(){
        // Промежуточный результат
        var exp : String = tvExpression.text.toString()
        if(exp.length == 0){
            tvResult.text = ""
            return
        }
        if(exp[exp.length - 1] == '*' || exp[exp.length - 1] == '+' || exp[exp.length - 1] == '-' || exp[exp.length - 1] == '/'){
            exp = exp.substring(0, exp.length - 1)
        }
        if(exp.length == 0){
            tvResult.text = ""
            return
        }
        if(curBal > 0)
            for(i in 1..curBal)
                exp = exp.plus(')')
        try{
            for(i : Int in 0 until exp.length){
                if(exp[i] != 'q') continue
                var bal : Int = 0
                var cnt : Int = 0
                var ind : Int = -1
                for(j : Int in (i + 4) until exp.length){
                    if(exp[j] == ')' && bal == 0){
                        ind = j
                        break
                    }
                    if(exp[j] == ',' && bal == 0) cnt++
                    if(exp[j] == '(') bal++
                    if(exp[j] == ')') bal--
                }
                if(ind == -1) break
                if(cnt > 0) continue
                exp = exp.substring(0, ind) + ",2" + exp.substring(ind, exp.length)
            }
            val expression = ExpressionBuilder(exp).function(sqrtXY).function(log).function(cot).function(acot).operator(factorial).build()
            val result = expression.evaluate()
            val longResult = result.toLong()
            var res : String = tvResult.text.toString()
            if(result == longResult.toDouble())
                res = longResult.toString()
            else
                res = result.toString()
            if(res == "NaN"){
                throw IllegalArgumentException("Bad Argument")
            }
            tvResult.text = res
        } catch (e : java.lang.Exception){

        }
    }
}