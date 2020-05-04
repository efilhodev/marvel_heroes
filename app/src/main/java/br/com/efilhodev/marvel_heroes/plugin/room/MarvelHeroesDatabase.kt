package br.com.efilhodev.marvel_heroes.plugin.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.efilhodev.marvel_heroes.model.Character

@Database(entities = [Character::class], version = 1)

abstract class MarvelHeroesDatabase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao

    companion object {
        private const val DB_NAME = "marvel_heroes.db"

        @Volatile
        private var INSTANCE: MarvelHeroesDatabase? = null

        @JvmStatic
        fun getInstanceDatabase(context: Context): MarvelHeroesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): MarvelHeroesDatabase {
            return Room.databaseBuilder(context, MarvelHeroesDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}