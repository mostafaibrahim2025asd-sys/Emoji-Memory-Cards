package com.example.emojimemorycards

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SCORE = "extra_score"
        const val EXTRA_ALL_MATCHED = "extra_all_matched"
        private const val GAME_SECONDS = 90L
        private const val TOTAL_CARDS = 12
        private const val TOTAL_PAIRS = 6
        private const val MISMATCH_DELAY_MS = 800L
    }

    private lateinit var tvTimer: TextView
    private lateinit var tvScore: TextView
    private lateinit var grid: GridLayout

    private val emojis = listOf("🐶", "🐱", "🍕", "🚀", "⚽", "🌈")
    private lateinit var cardEmojis: List<String>
    private val cardViews = mutableListOf<TextView>()
    private val matched = BooleanArray(TOTAL_CARDS)

    private var firstIndex = -1
    private var busy = false
    private var finished = false
    private var score = 0
    private var matchedPairs = 0

    private var timer: CountDownTimer? = null
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        tvTimer = findViewById(R.id.tvTimer)
        tvScore = findViewById(R.id.tvScore)
        grid = findViewById(R.id.gridCards)

        cardEmojis = (emojis + emojis).shuffled()

        buildGrid()
        updateScore()
        startTimer()
    }

    private fun buildGrid() {
        val density = resources.displayMetrics.density
        val margin = (6 * density).toInt()
        val cardHeight = (116 * density).toInt()

        for (i in 0 until TOTAL_CARDS) {
            val card = TextView(this)
            card.gravity = Gravity.CENTER
            card.textSize = 40f
            card.elevation = 4 * density
            card.isClickable = true
            card.isFocusable = true
            card.contentDescription = "Closed card"
            card.setBackgroundResource(R.drawable.bg_card_closed)
            card.setOnClickListener { onCardClicked(i) }

            val params = GridLayout.LayoutParams(
                GridLayout.spec(i / 3),
                GridLayout.spec(i % 3, 1f)
            )
            params.width = 0
            params.height = cardHeight
            params.setMargins(margin, margin, margin, margin)

            grid.addView(card, params)
            cardViews.add(card)
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(GAME_SECONDS * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.text = "Timer: ${(millisUntilFinished + 999) / 1000}s"
            }

            override fun onFinish() {
                tvTimer.text = "Timer: 0s"
                finishGame(false)
            }
        }
        timer?.start()
    }

    private fun onCardClicked(index: Int) {
        if (finished || busy || matched[index] || index == firstIndex) return

        showCard(index, R.drawable.bg_card_open, "")

        if (firstIndex == -1) {
            firstIndex = index
            return
        }

        val first = firstIndex
        firstIndex = -1

        if (cardEmojis[first] == cardEmojis[index]) {
            matched[first] = true
            matched[index] = true
            showCard(first, R.drawable.bg_card_matched, "matched")
            showCard(index, R.drawable.bg_card_matched, "matched")
            score += 1
            matchedPairs++
            updateScore()

            if (matchedPairs == TOTAL_PAIRS) {
                finishGame(true)
            }
        } else {
            busy = true
            showCard(first, R.drawable.bg_card_mismatched, "mismatched")
            showCard(index, R.drawable.bg_card_mismatched, "mismatched")
            score -= 1
            updateScore()

            handler.postDelayed({
                hideCard(first)
                hideCard(index)
                busy = false
            }, MISMATCH_DELAY_MS)
        }
    }

    private fun showCard(index: Int, backgroundRes: Int, state: String) {
        val card = cardViews[index]
        card.text = cardEmojis[index]
        card.setBackgroundResource(backgroundRes)
        card.contentDescription = (cardEmojis[index] + " " + state).trim()
    }

    private fun hideCard(index: Int) {
        val card = cardViews[index]
        card.text = ""
        card.setBackgroundResource(R.drawable.bg_card_closed)
        card.contentDescription = "Closed card"
    }

    private fun updateScore() {
        tvScore.text = "Score: $score"
    }

    private fun finishGame(allMatched: Boolean) {
        if (finished) return
        finished = true
        timer?.cancel()

        val delay = if (allMatched) 800L else 0L
        handler.postDelayed({
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(EXTRA_SCORE, score)
            intent.putExtra(EXTRA_ALL_MATCHED, allMatched)
            startActivity(intent)
            finish()
        }, delay)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        handler.removeCallbacksAndMessages(null)
    }
}