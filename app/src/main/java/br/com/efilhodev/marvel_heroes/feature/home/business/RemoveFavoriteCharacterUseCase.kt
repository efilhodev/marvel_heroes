package br.com.efilhodev.marvel_heroes.feature.home.business

import br.com.efilhodev.marvel_heroes.feature.base.business.UseCase
import br.com.efilhodev.marvel_heroes.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoveFavoriteCharacterUseCase @Inject constructor(private val repository: HomeRepository) :
    UseCase<Character, Int>() {

    override fun execute(param: Character?): Flow<Int> {
        return flowOf(repository.removeFavoriteCharacter(param!!)).map { param.id }
    }
}