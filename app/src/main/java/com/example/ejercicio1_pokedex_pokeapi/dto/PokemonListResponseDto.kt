package com.example.ejercicio1_pokedex_pokeapi.dto

data class PokemonListResponseDto (
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResultDto>
)
