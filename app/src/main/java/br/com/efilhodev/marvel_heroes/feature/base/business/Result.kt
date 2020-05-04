package br.com.efilhodev.marvel_heroes.feature.base.business

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

class ResultDispatcher<T> {
    var onSuccess: (result: Result.Success<T>) -> Unit = {}
    var onError: (error: Result.Error) -> Unit = {}
    var onLoading: (loading: Result.Loading) -> Unit = {}
}