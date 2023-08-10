package com.fitareq.programmingheroquiz.data.models

sealed class Data {
    data class Success(
        val data: List<Question> = listOf(),
    ) : Data()
    data class Error(val errorMessage: String) : Data()
    object Loading : Data()
}