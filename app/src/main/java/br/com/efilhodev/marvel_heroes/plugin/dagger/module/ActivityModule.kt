package br.com.efilhodev.marvel_heroes.plugin.dagger.module

import br.com.efilhodev.marvel_heroes.feature.base.view.HostActivity
import br.com.efilhodev.marvel_heroes.feature.details.view.CharacterDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeHostActivity(): HostActivity

    @ContributesAndroidInjector
    abstract fun contributeCharacterDetailActivity(): CharacterDetailActivity
}