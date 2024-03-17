package com.example.laba4mobiledop

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.color.utilities.Score

class GameActivity : AppCompatActivity() {
    private lateinit var chestsContainer: LinearLayout
    private lateinit var chests1Container: LinearLayout
    private lateinit var scoreText : TextView
    private lateinit var curScoreText : TextView
    private lateinit var curRoundText : TextView

    private lateinit var btn : Button
    private var score : Int = 0
    private var curRound : Int = 1
    private var curScore : Int = 0
    private lateinit var settings: Settings


    private val chestPoints = mutableListOf<Int>() // список очков для каждого сундука

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        chestsContainer = findViewById(R.id.forDynamicAddChest)
        chests1Container = findViewById(R.id.linearLayout4)
        btn = findViewById(R.id.button2)

        scoreText = findViewById(R.id.scoreText)
        curRoundText = findViewById(R.id.curRoundText)
        curScoreText = findViewById(R.id.curScoreText)

        settings = intent.getParcelableExtra<Settings>("settings")!!

        if (settings != null) {
            fillChests(settings.numberOfChests - 5,settings.numberOfTraps)
            // Распределяем ловушки и очки между сундуками
            distributeTrapsAndPoints(settings.numberOfChests,settings.numberOfTraps)
        }
        updateAllText()
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    private fun fillChests(chests: Int, traps: Int) {
        chests1Container.removeAllViews() // удаляем все существующие кнопки, если есть
        chestsContainer.removeAllViews()

        val few = btn.background
        for (i in 1..5) {
            val chestButton = Button(this)
            chestButton.background = few
            chestButton.id = i
            chestButton.text = "?${chestButton.id}"
            chestButton.setTextColor(btn.currentTextColor)

            chestButton.setOnClickListener { openChest(chestButton) }
            // Добавляем кнопку в контейнер
            chests1Container.addView(chestButton)
            // Добавляем 0 очков для каждого сундука
            chestPoints.add(0)
        }
        for (i in 1..chests + 1) {
            val chestButton = Button(this)
            chestButton.id = i + 5
            chestButton.background = few
            chestButton.text = "?${chestButton.id}"
            chestButton.setTextColor(btn.currentTextColor)

            chestButton.setOnClickListener { openChest(chestButton) }
            // Добавляем кнопку в контейнер
            chestsContainer.addView(chestButton)
            // Добавляем 0 очков для каждого сундука
            chestPoints.add(0)
        }
        chestsContainer.removeView(chestsContainer.findViewById(chests+6))


    }
    @SuppressLint("ResourceAsColor")
    private fun openChest(chest: Button) {
        // Открытие сундука и отображение очков или ловушки
        chest.isActivated = true
        chest.setTextColor(R.color.buttons)

        if (chestPoints[chest.id - 1] != Int.MIN_VALUE) {
            chest.text = "${chestPoints[chest.id - 1]}"
            curScore += chestPoints[chest.id - 1]
        }
        else {
            chest.text = "trap"
            resetRound()
            if (curRound > settings.numberOfRounds){
                finish()
            }
        }
        updateAllText()
    }
    private fun distributeTrapsAndPoints(chests: Int, traps: Int) {
        // Создаем список из lootPoints и traps
        val allPoints = MutableList(chests) { 0 } // все сундуки по умолчанию пустые
        for (i in 0 until chests - traps) {
            // Генерируем случайное количество очков для каждого сундука с очками
            val lootPoints = (1..10).random()
            allPoints[i] = lootPoints
        }
        for (i in (chests - traps) until chests) {
            // Последние traps сундуков - ловушки
            allPoints[i] = Int.MIN_VALUE // используем Int.MIN_VALUE для представления ловушки
        }
        allPoints.shuffle() // Перемешиваем список, чтобы очки и ловушки были распределены случайным образом

        for (i in allPoints)
            println(i)
        // Распределяем очки и ловушки между сундуками
        for (i in 0 until chests) {
            chestPoints[i] = allPoints[i]
            //println(chestPoints[i])
        }
    }

    fun updateAllText(){
        scoreText.text = getString(R.string.score_text, score.toString())
        curRoundText.text = getString(R.string.cur_round_text, curRound.toString())
        curScoreText.text = getString(R.string.cur_score_text, curScore.toString())
    }
    fun resetRound(){
        curScore = 0
        curRound++
        fillChests(settings.numberOfChests - 5,settings.numberOfTraps)
        distributeTrapsAndPoints(settings.numberOfChests,settings.numberOfTraps) // Распределяем ловушки и очки между сундуками
        updateAllText()
    }

    fun newRound(v : View){
        score += curScore
        curScore = 0
        curRound++
        if (curRound > settings.numberOfRounds){
            finish()
            return
        }
        fillChests(settings.numberOfChests - 5,settings.numberOfTraps)
        distributeTrapsAndPoints(settings.numberOfChests,settings.numberOfTraps) // Распределяем ловушки и очки между сундуками
        updateAllText()
    }
}