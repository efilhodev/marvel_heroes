package br.com.efilhodev.marvel_heroes.feature.home.gateway

import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import br.com.efilhodev.marvel_heroes.feature.home.business.GetCharactersUseCase
import br.com.efilhodev.marvel_heroes.feature.home.business.PageParams
import br.com.efilhodev.marvel_heroes.model.Character
import javax.inject.Inject

class GetCharactersDataSource @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    PositionalDataSource<Character>() {

    private val page = PageParams(DEFAULT_PAGE_LIMIT, 0)
    private lateinit var actionRetry: () -> Unit

    val state = MutableLiveData<DataSourceState>()

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Character>) {

        getCharactersUseCase.asFlow(param = page) {
            onLoading = { state.postValue(DataSourceState.LOADING) }

            onSuccess = { result ->
                callback.onResult(result.data, page.offset, page.limit)
                state.postValue(DataSourceState.DONE)
            }

            onError = {
                setInitialLoadRetryAction(params, callback)
                state.postValue(DataSourceState.ERROR)
            }
        }
    }

    private fun setInitialLoadRetryAction(
        params: LoadInitialParams, callback:
        LoadInitialCallback<Character>
    ) {
        actionRetry = { loadInitial(params, callback) }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Character>) {
        page.offset = params.startPosition

        getCharactersUseCase.asFlow(param = page) {
            onLoading = { state.postValue(DataSourceState.LOADING) }

            onSuccess = { result ->
                callback.onResult(result.data)
                state.postValue(DataSourceState.DONE)
            }

            onError = {
                setRangeLoadRetryAction(params, callback)
                state.postValue(DataSourceState.ERROR)
            }
        }
    }

    private fun setRangeLoadRetryAction(
        params: LoadRangeParams,
        callback: LoadRangeCallback<Character>
    ) {
        actionRetry = { loadRange(params, callback) }
    }

    fun doRetry() {
        actionRetry.invoke()
    }

    companion object {
        private const val DEFAULT_PAGE_LIMIT = 20
    }
}