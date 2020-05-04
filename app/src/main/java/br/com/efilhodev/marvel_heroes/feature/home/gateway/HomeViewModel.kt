package br.com.efilhodev.marvel_heroes.feature.home.gateway

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.efilhodev.marvel_heroes.feature.base.gateway.BaseViewModel
import br.com.efilhodev.marvel_heroes.feature.home.business.GetFavoriteCharactersIdsUseCase
import br.com.efilhodev.marvel_heroes.feature.home.business.RemoveFavoriteCharacterUseCase
import br.com.efilhodev.marvel_heroes.feature.home.business.SetFavoriteCharacterUseCase
import br.com.efilhodev.marvel_heroes.model.Character
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val factory: GetCharactersDataSourceFactory,
    private val setFavoriteCharacterUseCase: SetFavoriteCharacterUseCase,
    private val getFavoriteCharactersIdsUseCase: GetFavoriteCharactersIdsUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase
) :
    BaseViewModel() {

    val actionCharacterDataSourceState = factory.state
    val actionGetCharacters: LiveData<PagedList<Character>>
    val actionFavoritesCharactersIds: MutableLiveData<List<Int>> = MutableLiveData()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(5)
            .setInitialLoadSizeHint(5 * 2)
            .setEnablePlaceholders(false)
            .build()

        actionGetCharacters = LivePagedListBuilder<Int, Character>(factory, config).build()
    }

    fun retryCharactersLoad() {
        factory.retryLoad()
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
}