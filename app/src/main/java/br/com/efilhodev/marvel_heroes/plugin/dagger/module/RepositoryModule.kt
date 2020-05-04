package br.com.efilhodev.marvel_heroes.plugin.dagger.module

import br.com.efilhodev.marvel_heroes.feature.favorites.business.FavoritesRepository
import br.com.efilhodev.marvel_heroes.feature.home.business.HomeRepository
import br.com.efilhodev.marvel_heroes.plugin.repository.FavoritesRepositoryImpl
import br.com.efilhodev.marvel_heroes.plugin.repository.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
abstract class RepositoryModule {

    @Binds
    @Reusable
    abstract fun bindHomeRepository(repository: HomeRepositoryImpl): HomeRepository

    @Binds
    @Reusable
    abstract fun bindFavoritesRepository(repository: FavoritesRepositoryImpl): FavoritesRepository
}