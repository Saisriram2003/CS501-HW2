package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Color
import com.example.calculatorapp.databinding.ActivityMainBinding
import java.lang.Math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentNum: String = "0"

    private var operator: String = ""

    private var opClicked: Boolean = false

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
            if(!opClicked){
                operator("+")
                opClicked = true
            }
        }

        binding.buttonSub.setOnClickListener {
            if(!opClicked){
                operator("-")
                opClicked = true
            }
        }

        binding.buttonMult.setOnClickListener{
            if(!opClicked){
                operator("*")
                opClicked = true
            }
        }

        binding.buttonDiv.setOnClickListener {
            if(!opClicked){
                operator("/")
                opClicked = true
            }
        }

        binding.buttonSqrt.setOnClickListener {
            val res = sqrt(currentNum.toDouble())
            binding.editTextAnswer.setText(res.toString())
            currentNum = "0"
        }

        binding.buttonEqual.setOnClickListener {
            if (opClicked) {
                var result = 0.0
                var inputNumList = currentNum.split(operator)
                var firstNum = inputNumList[0].toDoubleOrNull()
                var secondNum = inputNumList[1].toDoubleOrNull()

                if (firstNum == null || secondNum == null) {
                    binding.editTextAnswer.setText("Please enter valid inputs.")
                } else {

                    when (operator) {
                        "+" -> result = firstNum + secondNum
                        "-" -> result = firstNum - secondNum
                        "*" -> result = firstNum * secondNum
                        "/" -> result = firstNum / secondNum
                    }
                    if(result > Double.MAX_VALUE || result < Double.MIN_VALUE){
                        binding.editTextAnswer.setText("The value is out of range.")
                    }
                    else{
                        binding.editTextAnswer.setText(result.toString())
                    }

                    // reset firstNum and secondNsum for another round of operation
                    opClicked = false
                    firstNum = 0.0
                    secondNum = 0.0
                    currentNum = "0"
                }
            }
            else{
                binding.editTextAnswer.setText("Please enter a valid equation")
            }
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
            // No operator has been clicked before:
            // set the operator
            // store the firstNum (by converting it)
            // clear the currentNum
        operator = op

        currentNum += op
        binding.editTextAnswer.setText(currentNum)
//        else {
            // operator has been pressed before:
            // replace the operator
//            operator = op
//        }
    }
}