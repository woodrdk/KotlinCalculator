package com.woodrdk.kotlincalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

import java.lang.NumberFormatException

import  kotlinx.android.synthetic.main.activity_main.*


private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_Operand1 = "Operand1"
private const val STATE_Operand1_Stored = "Operand1_Stored"

class MainActivity : AppCompatActivity() {

    // private lateinit var result: EditText
    // private lateinit var newNumber: EditText
    // private val displayOperation by lazy (LazyThreadSafetyMode.NONE){ findViewById<TextView>(R.id.operation)}

    // variables to hold the operands and type of calculation
    private var operand1: Double? = null
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        result = findViewById(R.id.result)
//        newNumber = findViewById(R.id.newNumber)
//
//        // Data input buttons
//        val button0: Button = findViewById(R.id.button0)
//        val button1: Button = findViewById(R.id.button1)
//        val button2: Button = findViewById(R.id.button2)
//        val button3: Button = findViewById(R.id.button3)
//        val button4: Button = findViewById(R.id.button4)
//        val button5: Button = findViewById(R.id.button5)
//        val button6: Button = findViewById(R.id.button6)
//        val button7: Button = findViewById(R.id.button7)
//        val button8: Button = findViewById(R.id.button8)
//        val button9: Button = findViewById(R.id.button9)
//        val buttonDot: Button = findViewById(R.id.buttonDot)
//
//        // Operations buttons
//        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
//        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
//        val buttonTimes = findViewById<Button>(R.id.buttonTimes)
//        val buttonPlus = findViewById<Button>(R.id.buttonPlus)
//        val buttonSub = findViewById<Button>(R.id.buttonSub)

        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try{
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)
            }
            catch (e: NumberFormatException){
                newNumber.setText("")
            }

            pendingOperation = op
            operation.text = pendingOperation
        }

        buttonEquals.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonTimes.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)
        buttonSub.setOnClickListener(opListener)

    }

    private fun performOperation(value: Double, operation: String){
        if(operand1 == null){
            operand1 = value
        }
        else{
            if(pendingOperation == "="){
                pendingOperation = operation
            }
            when (pendingOperation){
                "=" -> operand1 = value
                "/" -> operand1 = if(value == 0.0){
                          Double.NaN // handles attempt to divide by zero
                       }
                       else{
                         operand1!! / value
                       }
                "*" -> operand1 = operand1!! * value
                "+" -> operand1 = operand1!! + value
                "-" -> operand1 = operand1!! - value
            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(operand1 != null){
            outState.putDouble(STATE_Operand1, operand1!! )
            outState.putBoolean(STATE_Operand1_Stored, true)
        }
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if(savedInstanceState.getBoolean(STATE_Operand1_Stored,false)){
            savedInstanceState.getDouble(STATE_Operand1)
        }
        else{
            null
        }
        operand1 = savedInstanceState.getDouble(STATE_Operand1)

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION)
        operation.text = pendingOperation
    }
}
