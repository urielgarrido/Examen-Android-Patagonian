package com.example.patagonianexamen.viewModel.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.patagonianexamen.viewModel.SearchViewModel

class SearchViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel() as T
    }
}