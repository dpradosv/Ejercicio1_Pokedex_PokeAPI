package com.example.ejercicio1_pokedex_pokeapi.api

import com.example.ejercicio1_pokedex_pokeapi.dto.PokemonListResponseDto
import com.example.ejercicio1_pokedex_pokeapi.dto.PokemonDetailResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("api/v2/pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonListResponseDto>

    @GET("api/v2/pokemon/{id}")
    fun getPokemonDetail(
        @Path("id") id: Int
    ): Call<PokemonDetailResponseDto>

}

