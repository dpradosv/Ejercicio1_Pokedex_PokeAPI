package com.example.ejercicio1_pokedex_pokeapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ejercicio1_pokedex_pokeapi.model.PokemonDetail
import com.example.ejercicio1_pokedex_pokeapi.repository.PokemonRepository

class PokemonDetailViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val _pokemonDetail = MutableLiveData<PokemonDetail>()
    val pokemonDetail: LiveData<PokemonDetail> = _pokemonDetail

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getPokemonDetail(pokemonId: Int) {
        if (pokemonId <= 0) {
            _errorMessage.value = "Pokemon no valido"
            return
        }

        _isLoading.value = true
        repository.getPokemonDetail(
            pokemonId,
            { pokemonDetail ->
                _pokemonDetail.value = pokemonDetail
                _isLoading.value = false
            },
            { errorMessage ->
                _errorMessage.value = errorMessage
                _isLoading.value = false
            }
        )
    }
}
