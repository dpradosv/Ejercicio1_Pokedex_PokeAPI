package com.example.ejercicio1_pokedex_pokeapi.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val sprite: String,
    val types: List<String>,
    val height: Int,
    val weight: Int
)
