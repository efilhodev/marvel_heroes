package br.com.efilhodev.marvel_heroes.plugin.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.efilhodev.marvel_heroes.model.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteCharacter(character: Character)

    @Delete
    fun deleteFavoriteCharacter(character: Character)

    @Query("SELECT id FROM character")
    fun selectFavoritesCharactersIds(): Flow<List<Int>>

    @Query("SELECT * FROM character ORDER BY name ASC ")
    fun selectFavoritesCharacters(): Flow<List<Character>>
}