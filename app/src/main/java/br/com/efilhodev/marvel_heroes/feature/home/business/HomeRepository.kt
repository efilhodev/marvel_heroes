package br.com.efilhodev.marvel_heroes.feature.home.business

import br.com.efilhodev.marvel_heroes.model.Character
import br.com.efilhodev.marvel_heroes.model.CharacterDataWrapper
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getCharacters(params: PageParams): Flow<CharacterDataWrapper>
    fun getFavoritesCharactersIds(): Flow<List<Int>>
    fun setFavoriteCharacter(character: Character)
    fun removeFavoriteCharacter(character: Character)
}