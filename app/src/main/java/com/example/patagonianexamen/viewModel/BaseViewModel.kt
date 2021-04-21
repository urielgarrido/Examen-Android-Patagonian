package com.example.patagonianexamen.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    protected val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    protected val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje
}