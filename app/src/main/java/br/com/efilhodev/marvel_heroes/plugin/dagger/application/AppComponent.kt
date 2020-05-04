package br.com.efilhodev.marvel_heroes.plugin.dagger.application

import android.app.Application
import br.com.efilhodev.marvel_heroes.plugin.dagger.module.ActivityModule
import br.com.efilhodev.marvel_heroes.plugin.dagger.module.ApplicationModule
import br.com.efilhodev.marvel_heroes.plugin.dagger.module.DataSourceModule
import br.com.efilhodev.marvel_heroes.plugin.dagger.module.DatabaseModule
import br.com.efilhodev.marvel_heroes.plugin.dagger.module.FragmentModule
import br.com.efilhodev.marvel_heroes.plugin.dagger.module.NetworkModule
import br.com.efilhodev.marvel_heroes.plugin.dagger.module.RepositoryModule
import br.com.efilhodev.marvel_heroes.plugin.dagger.module.ViewModelFactoryModule
import br.com.efilhodev.marvel_heroes.plugin.dagger.module.ViewModelModule
import br.com.efilhodev.marvel_heroes.plugin.main.MarvelHeroesApplication
import br.com.efilhodev.marvel_heroes.plugin.room.CharacterDao
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        DatabaseModule::class,
        ActivityModule::class,
        FragmentModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class,
        DataSourceModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun injectApplication(application: MarvelHeroesApplication)

    fun characterDao(): CharacterDao
}