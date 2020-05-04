package br.com.efilhodev.marvel_heroes.plugin.repository

import br.com.efilhodev.marvel_heroes.feature.home.business.HomeRepository
import br.com.efilhodev.marvel_heroes.feature.home.business.PageParams
import br.com.efilhodev.marvel_heroes.model.Character
import br.com.efilhodev.marvel_heroes.model.CharacterDataWrapper
import br.com.efilhodev.marvel_heroes.plugin.room.CharacterDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao
) : BaseRepositoryImpl(), HomeRepository {

    override fun getCharacters(params: PageParams): Flow<CharacterDataWrapper> {
        return api.requestCharacters(limit = params.limit, offset = params.offset)
    }

    override fun getFavoritesCharactersIds(): Flow<List<Int>> {
        return characterDao.selectFavoritesCharactersIds()
    }

    override fun setFavoriteCharacter(character: Character) {
        return characterDao.insertFavoriteCharacter(character)
    }

    override fun removeFavoriteCharacter(character: Character) {
        return characterDao.deleteFavoriteCharacter(character)
    }
}