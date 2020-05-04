package br.com.efilhodev.marvel_heroes.feature.details.gateway

import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import br.com.efilhodev.marvel_heroes.feature.base.gateway.BaseViewModel
import br.com.efilhodev.marvel_heroes.feature.home.business.GetFavoriteCharactersIdsUseCase
import br.com.efilhodev.marvel_heroes.feature.home.business.RemoveFavoriteCharacterUseCase
import br.com.efilhodev.marvel_heroes.feature.home.business.SetFavoriteCharacterUseCase
import br.com.efilhodev.marvel_heroes.model.Character
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor(
    getFavoriteCharactersIdsUseCase: GetFavoriteCharactersIdsUseCase,
    private val setFavoriteCharacterUseCase: SetFavoriteCharacterUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase
) : BaseViewModel() {

    val favoritesIds = getFavoriteCharactersIdsUseCase.asLiveData {}.distinctUntilChanged()

    fun setOrRemoveFavoriteCharacter(character: Character?, isFavorite: Boolean?) {
        if(character != null && isFavorite != null){
            if (isFavorite.not()) setFavoriteCharacter(character)
            else removeFavoriteCharacter(character)
        }
    }

    private fun setFavoriteCharacter(character: Character) {
        setFavoriteCharacterUseCase.asFlow(viewModelScope, param = character) {
            onError = { postErrorMessage(it.throwable.message) }
        }
    }

    private fun removeFavoriteCharacter(character: Character) {
        removeFavoriteCharacterUseCase.asFlow(viewModelScope, param = character) {
            onError = { postErrorMessage(it.throwable.message) }
        }
    }
}