package br.com.efilhodev.marvel_heroes.feature.favorites.business

import br.com.efilhodev.marvel_heroes.model.Character
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavoritesCharacters(): Flow<List<Character>>
}