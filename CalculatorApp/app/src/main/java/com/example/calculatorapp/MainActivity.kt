package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.calculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentNum: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button0.setOnClickListener{
            inputNum(0)
        }

        binding.button1.setOnClickListener{
            inputNum(1)
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
}