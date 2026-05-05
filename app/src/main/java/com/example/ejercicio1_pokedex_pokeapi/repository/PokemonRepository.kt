package com.example.ejercicio1_pokedex_pokeapi.repository

import com.example.ejercicio1_pokedex_pokeapi.api.PokemonApiService
import com.example.ejercicio1_pokedex_pokeapi.dto.PokemonDetailResponseDto
import com.example.ejercicio1_pokedex_pokeapi.dto.PokemonListResponseDto
import com.example.ejercicio1_pokedex_pokeapi.model.Pokemon
import com.example.ejercicio1_pokedex_pokeapi.model.PokemonDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository(private val api: PokemonApiService) {

    fun getPokemonList(
        limit: Int,
        offset: Int,
        onSuccess: (List<Pokemon>) -> Unit,
        onError: (String) -> Unit
    ) {
        val call = api.getPokemonList(limit, offset)

        //para hacer el enqueue llamada asincrona, debemos pasarle un objeto callback con los dos metodos sobreescritos
        call.enqueue(object : Callback<PokemonListResponseDto> {
            override fun onResponse(
                p0: Call<PokemonListResponseDto>,
                //p1 es response, es decir la respuesta de la api
                response: Response<PokemonListResponseDto>
            ) {

                if (response.isSuccessful) {
                    val pokemonListResponseDto = response.body()
                    if (pokemonListResponseDto != null) {
                        val pokemonList = pokemonListResponseDto.results.map { pokemonResultDto ->
                            val id =
                                pokemonResultDto.url.trimEnd('/').substringAfterLast('/').toInt()
                            Pokemon(
                                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
                                pokemonResultDto.name,
                                id
                            )
                            //Item: imagen + nombre + id
                        }
                        onSuccess(pokemonList)
                    } else {
                        onError("La respuesta de la API llego vacia")
                    }
                } else {
                    onError("No se pudo obtener la lista de pokemon")
                }
            }

            override fun onFailure(
                p0: Call<PokemonListResponseDto>, p1: Throwable
            ) {
                onError("No se pudo conectar con la API")
            }
        })

    }

    fun getPokemonDetail(
        id: Int,
        onSuccess: (PokemonDetail) -> Unit,
        onError: (String) -> Unit
    ) {
        val call = api.getPokemonDetail(id)

        call.enqueue(object : Callback<PokemonDetailResponseDto> {
            override fun onResponse(
                p0: Call<PokemonDetailResponseDto>,
                response: Response<PokemonDetailResponseDto>
            ) {
                if (response.isSuccessful) {
                    val pokemonDetailResponseDto = response.body()
                    if (pokemonDetailResponseDto != null) {
                        val pokemonDetail = PokemonDetail(
                            pokemonDetailResponseDto.id,
                            pokemonDetailResponseDto.name,
                            pokemonDetailResponseDto.sprites.frontDefault
                                ?: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
                            pokemonDetailResponseDto.types.map { typeSlotDto ->
                                typeSlotDto.type.name
                            },
                            pokemonDetailResponseDto.height,
                            pokemonDetailResponseDto.weight
                        )
                        onSuccess(pokemonDetail)
                    } else {
                        onError("La respuesta de detalle llego vacia")
                    }
                } else {
                    onError("No se pudo obtener el detalle del pokemon")
                }
            }

            override fun onFailure(
                p0: Call<PokemonDetailResponseDto>,
                p1: Throwable
            ) {
                onError("No se pudo conectar con la API")
            }
        })
    }
}
