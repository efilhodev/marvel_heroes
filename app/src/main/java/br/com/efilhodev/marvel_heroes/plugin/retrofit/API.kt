package br.com.efilhodev.marvel_heroes.plugin.retrofit

import br.com.efilhodev.marvel_heroes.model.CharacterDataWrapper
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("v1/public/characters")
    fun requestCharacters(
        @Query("ts") ts: String = Authentication.timeStamp,
        @Query("hash") hash: String = Authentication.getHashKey(),
        @Query("apikey") apikey: String = Authentication.PUBLIC_KEY,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nameStartsWith") nameStartsWith: String?
    ): Flow<CharacterDataWrapper>
}