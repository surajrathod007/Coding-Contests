package com.surajrathod.codingcontests.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.surajrathod.codingcontests.model.ContestList
import com.surajrathod.codingcontests.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContestRepo {
    var _mutalbeAllContest = MutableLiveData<ContestList>()
    val AllContest : LiveData<ContestList>
    get() = _mutalbeAllContest

    var _loading = MutableLiveData<Boolean>()

    init {
        loadContest()
    }

    fun clear(){
    }

    fun refresh(){
        _mutalbeAllContest.value = _mutalbeAllContest.value
    }

    fun loadContest(){
        GlobalScope.launch(Dispatchers.IO){
            _loading.postValue(false)
            val l = NetworkService.apiInterface.getAllSites()
            _mutalbeAllContest.postValue(l)
            _loading.postValue(true)
        }
    }
}