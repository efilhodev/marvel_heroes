package br.com.efilhodev.marvel_heroes.feature.home.gateway

import androidx.paging.DataSource
import br.com.efilhodev.marvel_heroes.model.Character
import javax.inject.Inject

class GetCharactersDataSourceFactory @Inject constructor(private val source: GetCharactersDataSource) :
    DataSource.Factory<Int, Character>() {

    val state = source.state

    override fun create(): DataSource<Int, Character> {
        return source
    }

    fun retryCharactersLoad() {
        source.doRetry()
    }
}