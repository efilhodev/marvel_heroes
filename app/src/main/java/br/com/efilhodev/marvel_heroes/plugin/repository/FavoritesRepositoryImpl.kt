package br.com.efilhodev.marvel_heroes.plugin.repository

import br.com.efilhodev.marvel_heroes.feature.favorites.business.FavoritesRepository
import br.com.efilhodev.marvel_heroes.model.Character
import br.com.efilhodev.marvel_heroes.plugin.room.CharacterDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao
) : BaseRepositoryImpl(), FavoritesRepository {

    override fun getFavoritesCharacters(): Flow<List<Character>> {
        return characterDao.selectFavoritesCharacters()
    }
}