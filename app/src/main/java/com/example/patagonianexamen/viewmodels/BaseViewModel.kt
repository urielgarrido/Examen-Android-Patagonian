package com.example.patagonianexamen.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    protected val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    protected val _isNetOn = MutableLiveData<Boolean>()
    val isNetOn: LiveData<Boolean> = _isNetOn

    fun setShowLoading(show: Boolean){
        _showLoading.postValue(show)
    }
}