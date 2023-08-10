package com.fitareq.programmingheroquiz.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Questions(
    @SerializedName("questions")
    @Expose
    var questions: ArrayList<Question>
)


data class Answers(
    @SerializedName("A")
    @Expose
    var A: String? = null,
    @SerializedName("B")
    @Expose
    var B: String? = null,
    @SerializedName("C")
    @Expose
    var C: String? = null,
    @SerializedName("D")
    @Expose
    var D: String? = null
)

data class Question(

    @SerializedName("question")
    @Expose
    var question: String?,
    @SerializedName("answers")
    @Expose
    var answers: Answers?,
    @SerializedName("questionImageUrl")
    @Expose
    var questionImageUrl: String?,
    @SerializedName("correctAnswer")
    @Expose
    var correctAnswer: String?,
    @SerializedName("score")
    @Expose
    var score: Int?

)