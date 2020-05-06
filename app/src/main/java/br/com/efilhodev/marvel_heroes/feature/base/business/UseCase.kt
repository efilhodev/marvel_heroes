package br.com.efilhodev.marvel_heroes.feature.base.business

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import br.com.efilhodev.marvel_heroes.plugin.retrofit.API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

abstract class UseCase<P, R> {

    fun asFlow(
        scope: CoroutineScope = GlobalScope,
        param: P? = null,
        dsl: ResultDispatcher<R>.() -> Unit
    ) {
        val dispatcher = ResultDispatcher<R>()
            .apply(dsl)

        scope.launch {
            withContext(Dispatchers.IO) {
                execute(param)
                    .filter { guard(param) }
                    .onStart { dispatcher.onLoading(Result.Loading) }
                    .catch { error -> dispatcher.onError(Result.Error(error)) }
                    .collect { result -> dispatcher.onSuccess(Result.Success(result)) }
            }
        }
    }

    fun asLiveData(
        scope: CoroutineScope = GlobalScope,
        param: P? = null,
        dsl: ResultDispatcher<R>.() -> Unit
    ): LiveData<R> {
        val dispatcher = ResultDispatcher<R>()
            .apply(dsl)

        return execute(param)
            .filter { guard(param) }
            .onStart { dispatcher.onLoading(Result.Loading) }
            .catch { error -> dispatcher.onError(Result.Error(error)) }
            .asLiveData(scope.coroutineContext)
    }

    protected open suspend fun guard(param: P?): Boolean {
        return true
    }

    protected abstract fun execute(param: P?): Flow<R>
}