package com.surajrathod.codingcontests.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.surajrathod.codingcontests.repo.ContestRepo

class ContestViewModelFactory(val repo : ContestRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContestViewModel(repo) as T
    }
}