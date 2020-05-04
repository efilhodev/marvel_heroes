package br.com.efilhodev.marvel_heroes.plugin.dagger.module

import androidx.lifecycle.ViewModelProvider
import br.com.efilhodev.marvel_heroes.feature.base.gateway.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}