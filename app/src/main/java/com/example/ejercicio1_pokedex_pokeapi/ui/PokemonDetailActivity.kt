package com.example.ejercicio1_pokedex_pokeapi.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ejercicio1_pokedex_pokeapi.api.PokemonApiProvider
import com.example.ejercicio1_pokedex_pokeapi.databinding.ActivityPokemonDetailBinding
import com.example.ejercicio1_pokedex_pokeapi.repository.PokemonRepository
import com.example.ejercicio1_pokedex_pokeapi.viewmodel.PokemonDetailViewModel
import com.example.ejercicio1_pokedex_pokeapi.viewmodel.PokemonDetailViewModelFactory

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding
    private val apiService = PokemonApiProvider.apiService
    private val pokemonRepository = PokemonRepository(apiService)
    private val pokemonDetailViewModel: PokemonDetailViewModel by viewModels {
        PokemonDetailViewModelFactory(pokemonRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonId = intent.getIntExtra("pokemon_id", 0)

        pokemonDetailViewModel.pokemonDetail.observe(this) { pokemonDetail ->
            binding.layoutDetailError.visibility = View.GONE
            binding.tvPokemonDetailName.text = pokemonDetail.name
            binding.tvPokemonDetailId.text = "ID: ${pokemonDetail.id}"
            binding.tvPokemonDetailTypes.text = "Tipos: ${pokemonDetail.types.joinToString(", ")}"
            binding.tvPokemonDetailHeight.text = "Altura: ${pokemonDetail.height / 10.0} m"
            binding.tvPokemonDetailWeight.text = "Peso: ${pokemonDetail.weight / 10.0} kg"

            Glide.with(binding.root)
                .load(pokemonDetail.sprite)
                .into(binding.ivPokemonDetailSprite)
        }

        pokemonDetailViewModel.errorMessage.observe(this) { errorMessage ->
            binding.tvDetailErrorMessage.text = errorMessage
            binding.layoutDetailError.visibility = View.VISIBLE
        }

        pokemonDetailViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBarDetailLoading.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }

        binding.btnRetryDetail.setOnClickListener {
            binding.layoutDetailError.visibility = View.GONE
            pokemonDetailViewModel.getPokemonDetail(pokemonId)
        }

        pokemonDetailViewModel.getPokemonDetail(pokemonId)
    }
}
