package br.com.efilhodev.marvel_heroes.feature.favorites.business

import br.com.efilhodev.marvel_heroes.feature.base.business.UseCase
import br.com.efilhodev.marvel_heroes.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesCharactersUseCase @Inject constructor(private val repository: FavoritesRepository) :
    UseCase<Nothing, List<Character>>() {

    override fun execute(param: Nothing?): Flow<List<Character>> {
        return repository.getFavoritesCharacters()
    }
}