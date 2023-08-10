package com.fitareq.programmingheroquiz.data.repository

import com.fitareq.programmingheroquiz.data.api.ApiService
import com.fitareq.programmingheroquiz.data.models.Data
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAllQuestion(): Data {
        return try {
            val response = apiService.getAllQuestion()
            Data.Success(response.questions)
        } catch (e: Exception) {
            e.message?.let {
                if (it.contains("Unable to resolve host"))
                    Data.Error("No Internet")
            }
            Data.Error("Unexpected error!")
        }
    }
}