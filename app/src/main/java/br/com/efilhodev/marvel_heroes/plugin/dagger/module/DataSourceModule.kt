package br.com.efilhodev.marvel_heroes.plugin.dagger.module

import br.com.efilhodev.marvel_heroes.feature.home.business.GetCharactersUseCase
import br.com.efilhodev.marvel_heroes.feature.home.gateway.GetCharactersDataSource
import br.com.efilhodev.marvel_heroes.feature.home.gateway.GetCharactersDataSourceFactory
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    fun providesGetCharactersDataSource(getCharactersUseCase: GetCharactersUseCase): GetCharactersDataSource {
        return GetCharactersDataSource(getCharactersUseCase)
    }

    @Provides
    fun providesGetCharactersDataSourceFactory(source: GetCharactersDataSource): GetCharactersDataSourceFactory {
        return GetCharactersDataSourceFactory(source)
    }
}