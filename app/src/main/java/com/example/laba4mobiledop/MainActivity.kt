package com.example.laba4mobiledop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var countOfRound : EditText
    private lateinit var countOfTraps : EditText
    private lateinit var countOfChest : EditText
    private var settings : Settings = Settings(3,1,5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countOfRound = findViewById(R.id.countOfRoundText)
        countOfTraps = findViewById(R.id.countOfTrapsText)
        countOfChest = findViewById(R.id.countOfChestsText)
    }
    fun startButtonPress(v : View){
        var text : String = ""
        if (countOfRound.text.toString() != "" &&
            (countOfRound.text.toString().toInt() in 3..10))
            settings.numberOfRounds = countOfRound.text.toString().toInt()
        else
            text +="\nВведите правильно количество раундов"

        if (countOfTraps.text.toString() != "" &&
            (countOfTraps.text.toString().toInt() in 1..3))
            settings.numberOfTraps = countOfTraps.text.toString().toInt()
        else
            text +="\nВведите правильно количество ловушек"

        if (countOfChest.text.toString() != "" &&
            (countOfChest.text.toString().toInt() in 5..10))
            settings.numberOfChests = countOfChest.text.toString().toInt()
        else
            text +="\nВведите правильно количество сундуков"



        if (text != "")
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        else {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("settings", settings)
            startActivity(intent)
        }
    }
}