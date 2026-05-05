package com.example.ejercicio1_pokedex_pokeapi.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonApiProvider {

    private const val BASE_URL = "https://pokeapi.co/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: PokemonApiService = retrofit.create(PokemonApiService::class.java)
}