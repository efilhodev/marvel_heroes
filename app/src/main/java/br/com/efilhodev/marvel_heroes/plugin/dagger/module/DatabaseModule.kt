package br.com.efilhodev.marvel_heroes.plugin.dagger.module

import android.content.Context
import br.com.efilhodev.marvel_heroes.plugin.room.CharacterDao
import br.com.efilhodev.marvel_heroes.plugin.room.MarvelHeroesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context): MarvelHeroesDatabase {
        return MarvelHeroesDatabase.getInstanceDatabase(context)
    }

    @Provides
    @Singleton
    fun providesCharacterDao(database: MarvelHeroesDatabase): CharacterDao {
        return database.getCharacterDao()
    }
}