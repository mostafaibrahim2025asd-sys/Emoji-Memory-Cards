package com.example.emojimemorycards

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val score = intent.getIntExtra(GameActivity.EXTRA_SCORE, 0)
        val allMatched = intent.getBooleanExtra(GameActivity.EXTRA_ALL_MATCHED, false)

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = if (allMatched) "All Matched!" else "Time's Up!"

        val unit = if (score == 1 || score == -1) "Point" else "Points"
        val tvFinalScore = findViewById<TextView>(R.id.tvFinalScore)
        tvFinalScore.text = "$score $unit"

        val btnPlayAgain = findViewById<Button>(R.id.btnPlayAgain)
        btnPlayAgain.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
            finish()
        }
    }
}