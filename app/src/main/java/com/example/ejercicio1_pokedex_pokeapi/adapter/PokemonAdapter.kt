package com.example.ejercicio1_pokedex_pokeapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio1_pokedex_pokeapi.databinding.ItemPokemonBinding
import com.example.ejercicio1_pokedex_pokeapi.model.Pokemon

class PokemonAdapter(
    private val pokemonList: List<Pokemon>,
    private val onPokemonClick: (Pokemon) -> Unit
) :
    RecyclerView.Adapter<PokemonViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PokemonViewHolder {
        //infla ItemPokemonBinding con esta herramienta = LayoutInflater.from(p0.context)
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(p0: PokemonViewHolder, p1: Int) {
        p0.bind(pokemonList[p1], onPokemonClick)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}
