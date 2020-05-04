package br.com.efilhodev.marvel_heroes.feature.home.gateway

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import br.com.efilhodev.marvel_heroes.feature.home.business.GetCharactersUseCase
import br.com.efilhodev.marvel_heroes.feature.home.business.PageParams
import br.com.efilhodev.marvel_heroes.model.Character
import javax.inject.Inject

class GetCharactersDataSource @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    PageKeyedDataSource<Int, Character>() {

    private val page = PageParams(DEFAULT_PAGE_LIMIT, 0)
    private lateinit var action: () -> Unit

    val state = MutableLiveData<DataSourceState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        getCharactersUseCase.asFlow(param = page) {
            onLoading = { state.postValue(DataSourceState.LOADING) }

            onSuccess = { result ->
                callback.onResult(result.data, page.offset, page.limit)
                state.postValue(DataSourceState.DONE)
            }

            onError = {
                setInitialLoadRetry(params, callback)
                state.postValue(DataSourceState.ERROR)
            }
        }
    }

    private fun setInitialLoadRetry(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        action = { loadInitial(params, callback) }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Character>
    ) {
        page.offset = params.key

        getCharactersUseCase.asFlow(param = page) {
            onLoading = { state.postValue(DataSourceState.LOADING) }

            onSuccess = { result ->
                callback.onResult(result.data, params.key + page.limit)
                state.postValue(DataSourceState.DONE)
            }

            onError = {
                setAfterLoadRetry(params, callback)
                state.postValue(DataSourceState.ERROR)
            }
        }
    }

    private fun setAfterLoadRetry(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Character>
    ) {
        action = { loadAfter(params, callback) }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Character>
    ) {
    }

    fun retry() {
        action.invoke()
    }

    companion object {
        private const val DEFAULT_PAGE_LIMIT = 20
    }
}