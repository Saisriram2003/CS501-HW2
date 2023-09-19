package com.example.calculatorapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.calculatorapp.databinding.ActivityMainBinding
import kotlin.math.pow
import kotlin.math.sqrt

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentNum: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editTextAnswer.setText(currentNum)

        binding.button0.setOnClickListener{
            inputNum(0)
        }

        binding.button1.setOnClickListener{
            inputNum(1)
        }

        binding.button2.setOnClickListener{
            inputNum(2)
        }

        binding.button3.setOnClickListener{
            inputNum(3)
        }

        binding.button4.setOnClickListener{
            inputNum(4)
        }

        binding.button5.setOnClickListener{
            inputNum(5)
        }

        binding.button6.setOnClickListener{
            inputNum(6)
        }

        binding.button7.setOnClickListener{
            inputNum(7)
        }

        binding.button8.setOnClickListener{
            inputNum(8)
        }

        binding.button9.setOnClickListener{
            inputNum(9)
        }

        binding.buttonDot.setOnClickListener {
            if (currentNum.lastIndexOf('.') ==  -1) {
                currentNum += "."
                binding.editTextAnswer.setText(currentNum)
            }
        }

        binding.buttonPlus.setOnClickListener {
            operator("+")
        }

        binding.buttonSub.setOnClickListener {
            operator("-")
        }

        binding.buttonMult.setOnClickListener{
            operator("*")
        }

        binding.buttonDiv.setOnClickListener {
            operator("/")
        }

        binding.buttonSqrt.setOnClickListener {
            val res = sqrt(currentNum.toDouble())
            binding.editTextAnswer.setText(res.toString())
            currentNum = "0"
        }

        binding.buttonEqual.setOnClickListener {
            var expr = binding.editTextAnswer.text.toString()
            var ans = evaluateExpression(expr)
            Log.d(TAG, expr)
            Log.d(TAG, ans.toString())
            binding.editTextAnswer.setText(ans.toString())
        }

    }

    fun inputNum(num: Int) {
        if (currentNum.equals("0")) {
            currentNum = num.toString()
        } else {
            currentNum += num.toString()
        }
        binding.editTextAnswer.setText(currentNum)
    }

    fun operator(op: String) {
        currentNum += op
        binding.editTextAnswer.setText(currentNum)
    }
}
fun evaluateExpression(expression: String): Double {
    val sanitizedExpression = expression.replace(" ", "") // Remove spaces for simplicity
    val tokens = tokenizeExpression(sanitizedExpression)
    return evaluate(tokens)
}

fun tokenizeExpression(expression: String): List<String> {
    val operators = setOf("+", "-", "*", "/","s")
    val tokens = mutableListOf<String>()
    var currentToken = ""
    var i = 0
    while (i < expression.length) {
        var char = expression[i]
        if (char.toString() in operators) {
            if (currentToken.isNotEmpty()) {
                tokens.add(currentToken)
                currentToken = ""
            }
            if(char.toString() == "s"){
                i += 3
            }
            tokens.add(char.toString())
        } else {
            currentToken += char
        }
        i += 1
    }

    if (currentToken.isNotEmpty()) {
        tokens.add(currentToken)
    }

    return tokens
}

fun evaluate(tokens: List<String>): Double {
    Log.d(TAG, tokens.toString())
    val values = mutableListOf<Double>()
    val operators = mutableListOf<String>()

    for (token in tokens) {
        Log.d(TAG, operators.toString())
        when {
            // If number add it to values
            token.matches(Regex("[0-9]+")) -> values.add(token.toDouble())
            // If operator
            token in setOf("+", "-", "*", "/", "s") -> {
                while (operators.isNotEmpty() && precedence(operators.last()) >= precedence(token)) {
                    val operator = operators.removeAt(operators.size - 1)
                    if (operator == "s"){
                        val a = values.removeAt(values.size - 1)
                        val result = a.pow(0.5)
                        values.add(result)
                    }
                    else{
                        val b = values.removeAt(values.size - 1)
                        val a = values.removeAt(values.size - 1)
                        val result = applyOperator(a, b, operator)
                        values.add(result)
                    }

                }
                operators.add(token)
            }
            else -> throw IllegalArgumentException("Invalid token: $token")
        }
    }
    Log.d(TAG, values.toString())
    Log.d(TAG, operators.toString())

    while (operators.isNotEmpty()) {
        val operator = operators.removeAt(operators.size - 1)
        if (operator == "s"){
            val a = values.removeAt(values.size - 1)
            val result = a.pow(0.5)
            values.add(result)
        }
        else{
            val b = values.removeAt(values.size - 1)
            val a = values.removeAt(values.size - 1)
            val result = applyOperator(a, b, operator)
            values.add(result)
        }

    }

    if (values.size != 1) {
        throw IllegalArgumentException("Invalid expression")
    }

    return values[0]
}

fun precedence(operator: String): Int {
    return when (operator) {
        "+", "-" -> 1
        "*", "/" -> 2
        "s" -> 3
        else -> 0
    }
}

fun applyOperator(a: Double, b: Double, operator: String): Double {
    return when (operator) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> a / b
        else -> throw IllegalArgumentException("Invalid operator: $operator")
    }
}
