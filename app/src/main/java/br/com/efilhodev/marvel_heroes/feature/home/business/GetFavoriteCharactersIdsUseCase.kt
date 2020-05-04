package br.com.efilhodev.marvel_heroes.feature.home.business

import br.com.efilhodev.marvel_heroes.feature.base.business.Nothing
import br.com.efilhodev.marvel_heroes.feature.base.business.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteCharactersIdsUseCase @Inject constructor(
    private val repository: HomeRepository
) : UseCase<Nothing, List<Int>>() {

    override fun execute(param: Nothing?): Flow<List<Int>> {
        return repository.getFavoritesCharactersIds()
    }
}