package com.fitareq.programmingheroquiz.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.fitareq.programmingheroquiz.R
import com.fitareq.programmingheroquiz.data.models.Data
import com.fitareq.programmingheroquiz.data.models.Question
import com.fitareq.programmingheroquiz.databinding.ActivityMainBinding
import com.fitareq.programmingheroquiz.utils.Constants
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.Collections

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var questionList: ArrayList<Question> = arrayListOf()
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.questions.observe(this) {
            when (it) {
                is Data.Loading -> {
                     showLoadingView()
                }

                is Data.Success -> {
                    questionList = it.data
                    questionList.shuffle()
                    //questionList.shuffle()
                    loadQuestions()
                    hideLoadingView()
                }

                is Data.Error -> {
                    Log.v("homeItem", "error")
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
        viewModel.getHomeData()

        binding.apply {
            answerA.setOnClickListener {
                checkAnswer("A")
                restrictAnswerClick()
                countDownTimer.cancel()
                gotoNextQuestion()
            }
            answerB.setOnClickListener {
                checkAnswer("B")
                restrictAnswerClick()
                countDownTimer.cancel()
                gotoNextQuestion()
            }
            answerC.setOnClickListener {
                checkAnswer("C")
                restrictAnswerClick()
                countDownTimer.cancel()
                gotoNextQuestion()
            }
            answerD.setOnClickListener {
                checkAnswer("D")
                restrictAnswerClick()
                countDownTimer.cancel()
                gotoNextQuestion()
            }
        }
    }

    private fun gotoNextQuestion() {
        ++currentQuestionIndex
        //showLoadingDialog()
       if (currentQuestionIndex < questionList.size) {
        handler.postDelayed({
            loadQuestions()
        }, 2000)
        } else {
            handler.postDelayed({
                startActivity(
                    Intent(this, EndActivity::class.java)
                        .putExtra(Constants.KEY_TOTAL_QUESTION, questionList.size.toString())
                            .putExtra(Constants.KEY_SCORE, currentScore.toString())
                        .putExtra(Constants.KEY_CORRECT_ANS, correctAns.toString())
                )
                finish()
            }, 2000)
        }
    }

    private fun restrictAnswerClick() {
        binding.apply {
            answerA.isClickable = false
            answerB.isClickable = false
            answerC.isClickable = false
            answerD.isClickable = false
        }

    }

    private fun enableAnswerClick() {
        binding.apply {
            answerA.isClickable = true
            answerB.isClickable = true
            answerC.isClickable = true
            answerD.isClickable = true
        }

    }

    private fun setAnswerCorrect(ans: TextView) {
        ans.apply {
            isEnabled = false
            isSelected = true
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))
        }
    }

    private fun setAnswerIncorrect(ans: TextView) {
        ans.apply {
            isEnabled = false
            isSelected = false
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))
        }
    }

    private fun setAnswersToDefaultState() {
        binding.apply {
            answerA.apply {
                isEnabled = true
                isSelected = true
            }
            answerB.apply {
                isEnabled = true
                isSelected = true
            }
            answerC.apply {
                isEnabled = true
                isSelected = true
            }
            answerD.apply {
                isEnabled = true
                isSelected = true
            }
        }
    }


    private fun checkAnswer(checkedAnswer: String) {
        when (checkedAnswer) {
            "A" -> {
                if (checkedAnswer.equals(currentCorrectAnswer, true)) {
                    setAnswerCorrect(binding.answerA)
                    currentScore += currentQuestion.score!!
                    ++correctAns
                } else {
                    setAnswerIncorrect(binding.answerA)
                    selectCorrectAnswer()
                }
            }

            "B" -> {
                if (checkedAnswer.equals(currentCorrectAnswer, true)) {
                    setAnswerCorrect(binding.answerB)
                    currentScore += currentQuestion.score!!
                    ++correctAns
                } else {
                    setAnswerIncorrect(binding.answerB)
                    selectCorrectAnswer()
                }
            }

            "C" -> {
                if (checkedAnswer.equals(currentCorrectAnswer, true)) {
                    setAnswerCorrect(binding.answerC)
                    currentScore += currentQuestion.score!!
                    ++correctAns
                } else {
                    setAnswerIncorrect(binding.answerC)
                    selectCorrectAnswer()
                }
            }

            "D" -> {
                if (checkedAnswer.equals(currentCorrectAnswer, true)) {
                    setAnswerCorrect(binding.answerD)
                    currentScore += currentQuestion.score!!
                    ++correctAns
                } else {
                    setAnswerIncorrect(binding.answerD)
                    selectCorrectAnswer()
                }
            }
        }
    }

    private fun selectCorrectAnswer() {
        when (currentCorrectAnswer) {
            "A" -> {
                setAnswerCorrect(binding.answerA)
            }

            "B" -> {
                setAnswerCorrect(binding.answerB)
            }

            "C" -> {
                setAnswerCorrect(binding.answerC)
            }

            "D" -> {
                setAnswerCorrect(binding.answerD)
            }
        }
    }

    private var currentQuestionIndex = 0
    private var currentScore = 0
    private var currentCorrectAnswer = ""
    private var correctAns = 0
    private lateinit var currentQuestion: Question
    private lateinit var countDownTimer: CountDownTimer
    private fun loadQuestions() {
        if (questionList.size > 0 && currentQuestionIndex < questionList.size) {

            setAnswersToDefaultState()
            enableAnswerClick()
            currentQuestion = questionList[currentQuestionIndex]
            currentCorrectAnswer = currentQuestion.correctAnswer
            binding.apply {
                questionCounter.text = getString(
                    R.string.question,
                    (currentQuestionIndex + 1).toString(),
                    questionList.size.toString()
                )
                score.text = getString(R.string.score, currentScore.toString())
                questionPoint.text = getString(R.string.point, currentQuestion.score.toString())
                if (currentQuestion.questionImageUrl.isNullOrEmpty()) {
                    questionImage.visibility = View.GONE
                } else {
                    Picasso.get().load(currentQuestion.questionImageUrl).into(questionImage)
                    questionImage.visibility = View.VISIBLE
                }
                question.text = currentQuestion.question

                val currentAnswer = currentQuestion.answers
                if (currentAnswer.A.isNullOrEmpty()) {
                    answerA.visibility = View.GONE
                } else {
                    answerA.text = currentAnswer.A
                    answerA.visibility = View.VISIBLE
                }

                if (currentAnswer.B.isNullOrEmpty()) {
                    answerB.visibility = View.GONE
                } else {
                    answerB.text = currentAnswer.B
                    answerB.visibility = View.VISIBLE
                }

                if (currentAnswer.C.isNullOrEmpty()) {
                    answerC.visibility = View.GONE
                } else {
                    answerC.text = currentAnswer.C
                    answerC.visibility = View.VISIBLE
                }

                if (currentAnswer.D.isNullOrEmpty()) {
                    answerD.visibility = View.GONE
                } else {
                    answerD.text = currentAnswer.D
                    answerD.visibility = View.VISIBLE
                }
            }
            //hideLoadingDialog()
            startCountdownTimer()
        }
    }

    private fun startCountdownTimer() {
        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(remainingTimeMillis: Long) {
                updateCountdownUI(remainingTimeMillis)
            }

            override fun onFinish() {
                gotoNextQuestion()
            }

        }
        countDownTimer.start()
    }

    private fun updateCountdownUI(remainingTimeMillis: Long) {
        binding.composeView.setContent {
            CountdownTimerScreen(
                countdownDurationMillis = remainingTimeMillis,
            )
        }
    }


    @Composable
    fun CountdownTimerScreen(
        countdownDurationMillis: Long
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //CountDownLinearProgress(countdownDurationMillis)
            CountdownTimerText(countdownDurationMillis)
        }
    }

    @Composable
    fun CountdownTimerText(remainingTimeMillis: Long) {
        val seconds = (remainingTimeMillis / 1000).toString()
        Text(text = "Time left: $seconds seconds", fontSize = 18.sp, color = Color.White)
    }

    @Composable
    fun CountDownLinearProgress(remainingTimeMillis: Long) {
        val progrs by remember { mutableFloatStateOf(1.0f / ((10000 - remainingTimeMillis) / 1000)) }
        Log.v("linearProgress", "$progrs")
        val progress = 1.0f / (remainingTimeMillis / 1000)
        LinearProgressIndicator(progress = progrs)
    }

    private fun showLoadingView() {
       binding.apply {
           composeView.visibility = View.GONE
           scoreLay.visibility = View.GONE
           questionLay.visibility = View.GONE
           answerLay.visibility = View.GONE

           loading.visibility = View.VISIBLE
       }
    }

    private fun hideLoadingView() {
        binding.apply {
            composeView.visibility = View.VISIBLE
            scoreLay.visibility = View.VISIBLE
            questionLay.visibility = View.VISIBLE
            answerLay.visibility = View.VISIBLE

            loading.visibility = View.GONE
        }
    }
}