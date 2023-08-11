package com.fitareq.programmingheroquiz.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitareq.programmingheroquiz.R
import com.fitareq.programmingheroquiz.databinding.ActivityEndBinding
import com.fitareq.programmingheroquiz.utils.Constants

class EndActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEndBinding
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = this.getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE)

        val totalQues = intent.getStringExtra(Constants.KEY_TOTAL_QUESTION) ?: ""
        val score = intent.getStringExtra(Constants.KEY_SCORE) ?: ""
        val correctAns = intent.getStringExtra(Constants.KEY_CORRECT_ANS) ?: ""
        val prevHighScore = getPreviousHighScore()

        binding.apply {
            yourScore.text = getString(R.string.your_score, score)
            totalQuestion.text = getString(R.string.total_question, totalQues)
            correctAnswer.text = getString(R.string.correct_answer, correctAns)

            if (score.toInt() > prevHighScore)
            {
                highScore.text = getString(R.string.high_score, "New ", score)
                saveNewHighScore(score.toInt())
            }else highScore.text = getString(R.string.high_score, "", prevHighScore.toString())

            returnBtn.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun getPreviousHighScore(): Int {
        return sharedPref.getInt(Constants.KEY_HIGH_SCORE, 0)
    }

    private fun saveNewHighScore(score: Int) {
        val editor = sharedPref.edit()
        editor.putInt(Constants.KEY_HIGH_SCORE, score)
        editor.apply()
    }
}