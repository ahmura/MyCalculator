package com.ahmura.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if(lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEquals(view: View) {
        if(lastNumeric) {
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var firstNumber = splitValue[0]
                    var secondNumber = splitValue[1]
                    if(!prefix.isEmpty()) {
                        firstNumber = prefix + firstNumber
                    }
                    tvInput.text = removeZeroAfterDot((firstNumber.toDouble() - secondNumber.toDouble()).toString())
                } else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var firstNumber = splitValue[0]
                    var secondNumber = splitValue[1]
                    if(!prefix.isEmpty()) {
                        firstNumber = prefix + firstNumber
                    }
                    tvInput.text = removeZeroAfterDot((firstNumber.toDouble() / secondNumber.toDouble()).toString())
                } else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var firstNumber = splitValue[0]
                    var secondNumber = splitValue[1]
                    if(!prefix.isEmpty()) {
                        firstNumber = prefix + firstNumber
                    }
                    tvInput.text = removeZeroAfterDot((firstNumber.toDouble() + secondNumber.toDouble()).toString())
                } else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var firstNumber = splitValue[0]
                    var secondNumber = splitValue[1]
                    if(!prefix.isEmpty()) {
                        firstNumber = prefix + firstNumber
                    }
                    tvInput.text = removeZeroAfterDot((firstNumber.toDouble() * secondNumber.toDouble()).toString())
                }



            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if(result.endsWith(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    fun onOperator(view: View) {
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if (value.startsWith("-")) {
            false
        }
        else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }
}