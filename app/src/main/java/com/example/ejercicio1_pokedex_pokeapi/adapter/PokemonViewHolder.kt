package com.example.ejercicio1_pokedex_pokeapi.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejercicio1_pokedex_pokeapi.databinding.ItemPokemonBinding
import com.example.ejercicio1_pokedex_pokeapi.model.Pokemon

class PokemonViewHolder(private val binding: ItemPokemonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: Pokemon, onPokemonClick: (Pokemon) -> Unit) {
        Glide.with(binding.root)
            .load(pokemon.image)
            .into(binding.ivPokemonImage)

        binding.tvPokemonName.text = pokemon.name
        binding.tvPokemonId.text = pokemon.id.toString()

        binding.root.setOnClickListener {
            onPokemonClick(pokemon)
        }
    }
}
