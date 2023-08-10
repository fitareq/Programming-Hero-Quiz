package com.fitareq.programmingheroquiz.data.api

import com.fitareq.programmingheroquiz.data.models.Questions
import retrofit2.http.GET

interface ApiService {
    @GET("quiz.json")
    suspend fun getAllQuestion(): Questions
}