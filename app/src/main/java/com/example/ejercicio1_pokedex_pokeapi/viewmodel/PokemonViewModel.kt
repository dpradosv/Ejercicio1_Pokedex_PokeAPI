package com.example.ejercicio1_pokedex_pokeapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ejercicio1_pokedex_pokeapi.model.Pokemon
import com.example.ejercicio1_pokedex_pokeapi.repository.PokemonRepository

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    //el 20 es fijo, siempre queremos 20 pokemons
    private val limit = 20
    private var offset = 0
    private var currentPage = 1
    private val maxPage = 3

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _canGoPrevious = MutableLiveData(false)
    val canGoPrevious: LiveData<Boolean> = _canGoPrevious

    private val _canGoNext = MutableLiveData(true)
    val canGoNext: LiveData<Boolean> = _canGoNext


    fun getPokemons() {
        _isLoading.value = true
        repository.getPokemonList(
            limit, offset,
            { pokemonList ->
                _pokemonList.value = pokemonList
                _isEmpty.value = pokemonList.isEmpty()
                _isLoading.value = false
            },
            //onError
            { errorMessage ->
                _errorMessage.value = errorMessage
                _isLoading.value = false
            }
        )

    }

    fun loadNextPage() {
        if (currentPage == maxPage) {
            return
        }
        currentPage++
        offset += limit
        updatePaginationButtons()
        getPokemons()
    }

    fun loadPreviousPage() {
        if (currentPage == 1) {
            return
        }
        currentPage--
        offset -= limit
        updatePaginationButtons()
        getPokemons()
    }

    private fun updatePaginationButtons() {
        _canGoPrevious.value = currentPage > 1
        _canGoNext.value = currentPage < maxPage
    }
}
