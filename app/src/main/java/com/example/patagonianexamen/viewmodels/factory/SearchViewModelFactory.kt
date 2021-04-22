package com.example.patagonianexamen.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.patagonianexamen.repository.Repository
import com.example.patagonianexamen.viewmodels.SearchViewModel

class SearchViewModelFactory(
    private val repository: Repository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SearchViewModel(repository) as T
    }
}