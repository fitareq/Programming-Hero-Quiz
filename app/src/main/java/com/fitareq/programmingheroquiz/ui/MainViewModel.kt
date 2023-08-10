package com.fitareq.programmingheroquiz.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitareq.programmingheroquiz.data.models.Data
import com.fitareq.programmingheroquiz.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
):ViewModel() {
    private val _questions = MutableLiveData<Data>()
    val questions : LiveData<Data> = _questions

    fun getHomeData(){
        _questions.postValue(Data.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllQuestion().let {
                _questions.postValue(it)
            }
        }
    }
}