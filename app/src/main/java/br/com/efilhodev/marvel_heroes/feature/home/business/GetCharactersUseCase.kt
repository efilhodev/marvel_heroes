package br.com.efilhodev.marvel_heroes.feature.home.business

import br.com.efilhodev.marvel_heroes.feature.base.business.UseCase
import br.com.efilhodev.marvel_heroes.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<PageParams, List<Character>>() {

    override fun execute(param: PageParams?): Flow<List<Character>> {
        return repository.getCharacters(param!!)
            .map { wrapper -> wrapper.data }
            .map { container -> container.results }
    }
}