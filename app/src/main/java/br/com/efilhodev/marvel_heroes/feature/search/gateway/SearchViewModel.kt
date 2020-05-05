package br.com.efilhodev.marvel_heroes.feature.search.gateway

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.efilhodev.marvel_heroes.feature.base.gateway.BaseViewModel
import br.com.efilhodev.marvel_heroes.feature.home.business.GetCharactersUseCase
import br.com.efilhodev.marvel_heroes.feature.home.business.GetFavoriteCharactersIdsUseCase
import br.com.efilhodev.marvel_heroes.feature.home.business.PageParams
import br.com.efilhodev.marvel_heroes.feature.home.business.RemoveFavoriteCharacterUseCase
import br.com.efilhodev.marvel_heroes.feature.home.business.SetFavoriteCharacterUseCase
import br.com.efilhodev.marvel_heroes.feature.home.gateway.DataSourceState
import br.com.efilhodev.marvel_heroes.model.Character
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val setFavoriteCharacterUseCase: SetFavoriteCharacterUseCase,
    private val getFavoriteCharactersIdsUseCase: GetFavoriteCharactersIdsUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase
) : BaseViewModel() {

    val getCharacters = MutableLiveData<List<Character>>()
    val state = MutableLiveData<DataSourceState>()
    val actionFavoritesCharactersIds: MutableLiveData<List<Int>> = MutableLiveData()

    fun getCharactersByName(name: String) {
        val page = PageParams(DEFAULT_SEARCH_BY_NAME_LIMIT, query = name)
        getCharactersUseCase.asFlow(viewModelScope, param = page) {
            onLoading = { state.postValue(DataSourceState.LOADING) }

            onSuccess = {
                state.postValue(DataSourceState.DONE)
                getCharacters.postValue(it.data)
            }

            onError = {
                state.postValue(DataSourceState.ERROR)
                postErrorMessage(it.throwable.message)
            }
        }
    }

    fun setOrRemoveFavoriteCharacter(character: Character, isFavorite: Boolean) {
        if (isFavorite.not()) setFavoriteCharacter(character)
        else removeFavoriteCharacter(character)
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

    fun getFavoritesCharactersIds() {
        getFavoriteCharactersIdsUseCase.asFlow {
            onSuccess = { actionFavoritesCharactersIds.postValue(it.data) }
        }
    }

    companion object {
        private const val DEFAULT_SEARCH_BY_NAME_LIMIT = 100
    }
}

