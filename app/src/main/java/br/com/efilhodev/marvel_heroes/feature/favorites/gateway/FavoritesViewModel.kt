package br.com.efilhodev.marvel_heroes.feature.favorites.gateway

import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import br.com.efilhodev.marvel_heroes.feature.base.gateway.BaseViewModel
import br.com.efilhodev.marvel_heroes.feature.favorites.business.GetFavoritesCharactersUseCase
import br.com.efilhodev.marvel_heroes.feature.home.business.RemoveFavoriteCharacterUseCase
import br.com.efilhodev.marvel_heroes.model.Character
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    getFavoritesCharactersUseCase: GetFavoritesCharactersUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase
) : BaseViewModel() {

    val favoritesCharacters = getFavoritesCharactersUseCase.asLiveData {}.distinctUntilChanged()

    fun removeFavoriteCharacter(character: Character) {
        removeFavoriteCharacterUseCase.asFlow(viewModelScope, param = character) {}
    }
}