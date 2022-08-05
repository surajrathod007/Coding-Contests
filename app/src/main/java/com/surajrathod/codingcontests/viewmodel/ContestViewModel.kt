package com.surajrathod.codingcontests.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.surajrathod.codingcontests.model.Contest
import com.surajrathod.codingcontests.model.ContestList
import com.surajrathod.codingcontests.repo.ContestRepo

class ContestViewModel(val repo : ContestRepo) : ViewModel() {

    var _mutableSites = MutableLiveData<List<Contest>>()
    val siteList : LiveData<List<Contest>>
    get() = _mutableSites




    init {
        _mutableSites.postValue(repo.AllContest.value)
    }


    fun getAllSites() : LiveData<ContestList>{
        return repo.AllContest
    }
}