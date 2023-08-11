package com.fitareq.programmingheroquiz.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitareq.programmingheroquiz.R
import com.fitareq.programmingheroquiz.databinding.ActivityStartBinding
import com.fitareq.programmingheroquiz.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        binding.highScore.text = getString(R.string.point, getPreviousHighScore().toString())
    }
    private fun getPreviousHighScore(): Int {
        val sharedPref = this.getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE)
        return sharedPref.getInt(Constants.KEY_HIGH_SCORE, 0)
    }
}