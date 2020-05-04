package br.com.efilhodev.marvel_heroes.plugin.dagger.module

import br.com.efilhodev.marvel_heroes.feature.favorites.view.FavoritesFragment
import br.com.efilhodev.marvel_heroes.feature.home.view.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoritesFragment(): FavoritesFragment
}