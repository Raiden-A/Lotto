package com.js.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

import com.js.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val numberTextList: List<TextView> by lazy {
        listOf<TextView>(
            binding.num1Tv,
            binding.num2Tv,
            binding.num3Tv,
            binding.num4Tv,
            binding.num5Tv,
            binding.num6Tv


        )
    }

    private var didRun = false
    private var pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.numberPicker.minValue = 1
        binding.numberPicker.maxValue = 45

        initAddButton()
        initClearButton()
        initRunBotton()


    }

    private fun initClearButton() {
        binding.clearButton.setOnClickListener {
            didRun = false
            pickNumberSet.clear()
            numberTextList.forEach {
                it.isVisible = false
            }

        }
    }

    private fun initAddButton() {
        binding.addButton.setOnClickListener {

            if (didRun) {
                Toast.makeText(
                    this,
                    "초기화 수 시도해주세요",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 5) {
                Toast.makeText(
                    this,
                    "번호는 5개 까지만 입력 가능합니다. 자동 생성 시작을 눌러주세요 ",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            var pickNum = binding.numberPicker.value

            if(pickNumberSet.contains(pickNum)){
                Toast.makeText(
                    this,
                    "번호는 중복선택하실 수 없습니다.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            val textView = numberTextList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = pickNum.toString()

            setNumberbackground(binding.numberPicker.value, textView)
            pickNumberSet.add(binding.numberPicker.value)
        }
    }

    private fun initRunBotton() {
        binding.runButton.setOnClickListener {
            val list = getRandomNumber()
            didRun = true
            list.forEachIndexed { index, number ->
                val textView = numberTextList[index]
                textView.text = number.toString()
                textView.isVisible = true

                setNumberbackground(number, textView)
            }
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberLIst = mutableListOf<Int>().apply {
            for (i in 1..45) {
                if (pickNumberSet.contains(i))
                    continue
                this.add(i)
            }
        }
        numberLIst.shuffle()
        val numList = pickNumberSet.toList() +
                numberLIst.subList(0, 6 - pickNumberSet.size)
        return numList.sorted()
    }

    private fun setNumberbackground(number: Int, tv: TextView) {
        when (number) {
            in 1..10 -> tv.background =
                ContextCompat.getDrawable(
                    this,
                    R.drawable.circle_yellow
                )
            in 11..20 -> tv.background =
                ContextCompat.getDrawable(
                    this,
                    R.drawable.circle_blue
                )
            in 21..30 -> tv.background =
                ContextCompat.getDrawable(
                    this,
                    R.drawable.circle_red
                )
            in 31..40 -> tv.background =
                ContextCompat.getDrawable(
                    this,
                    R.drawable.circle_gray
                )
            else -> tv.background =
                ContextCompat.getDrawable(
                    this,
                    R.drawable.circle_green
                )

        }
    }


}