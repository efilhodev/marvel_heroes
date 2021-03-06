package br.com.efilhodev.marvel_heroes.plugin.dagger.module

import androidx.lifecycle.ViewModel
import br.com.efilhodev.marvel_heroes.feature.details.gateway.CharacterDetailViewModel
import br.com.efilhodev.marvel_heroes.feature.favorites.gateway.FavoritesViewModel
import br.com.efilhodev.marvel_heroes.feature.home.gateway.HomeViewModel
import br.com.efilhodev.marvel_heroes.feature.search.gateway.SearchViewModel
import br.com.efilhodev.marvel_heroes.plugin.dagger.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindCharacterListViewModel(viewmodel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(viewmodel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailViewModel::class)
    abstract fun bindCharacterDetailViewModel(viewmodel: CharacterDetailViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewmodel: SearchViewModel): ViewModel
}