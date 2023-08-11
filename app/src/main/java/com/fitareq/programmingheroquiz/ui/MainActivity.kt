package com.fitareq.programmingheroquiz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.fitareq.programmingheroquiz.R
import com.fitareq.programmingheroquiz.data.models.Data
import com.fitareq.programmingheroquiz.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.questions.observe(this){
            when (it) {
                is Data.Loading -> {
                    Log.v("homeItem", "loading")
                }

                is Data.Success -> {
                    Log.v("homeItem", "success")
                }

                is Data.Error -> {
                    Log.v("homeItem", "error")
                }
            }
        }
        viewModel.getHomeData()

        binding.answerA.apply {
            isEnabled = false
            isSelected = true
            setTextColor(ContextCompat.getColor(this@MainActivity,R.color.black))
        }

        binding.answerB.apply {
            isEnabled = false
            isSelected = false
            setTextColor(ContextCompat.getColor(this@MainActivity,R.color.black))
        }
    }
}