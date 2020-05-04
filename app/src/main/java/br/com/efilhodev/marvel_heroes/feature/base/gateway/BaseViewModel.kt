package br.com.efilhodev.marvel_heroes.feature.base.gateway

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    internal val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun postErrorMessage(message: String?) {
        message?.let { errorMessage.postValue(it) }
    }
}