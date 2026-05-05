package com.example.ejercicio1_pokedex_pokeapi.dto

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponseDto(
    val id: Int,
    val name: String,
    val sprites: PokemonSpritesDto,
    val types: List<PokemonTypeSlotDto>,
    val height: Int,
    val weight: Int
)

data class PokemonSpritesDto(
    @SerializedName("front_default")
    val frontDefault: String?
)

data class PokemonTypeSlotDto(
    val type: PokemonTypeDto
)

data class PokemonTypeDto(
    val name: String
)
