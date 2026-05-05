package com.example.ejercicio1_pokedex_pokeapi.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio1_pokedex_pokeapi.adapter.PokemonAdapter
import com.example.ejercicio1_pokedex_pokeapi.api.PokemonApiProvider
import com.example.ejercicio1_pokedex_pokeapi.databinding.ActivityMainBinding
import com.example.ejercicio1_pokedex_pokeapi.repository.PokemonRepository
import com.example.ejercicio1_pokedex_pokeapi.viewmodel.PokemonViewModel
import com.example.ejercicio1_pokedex_pokeapi.viewmodel.PokemonViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val apiService = PokemonApiProvider.apiService
    private val pokemonRepository = PokemonRepository(apiService)
    private val pokemonViewModel: PokemonViewModel by viewModels {
        PokemonViewModelFactory(pokemonRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener((binding.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //LAYOUT
        binding.rvPokemons.layoutManager = LinearLayoutManager(this)

        //ADAPTER Y ASIGANR LISTA
        pokemonViewModel.pokemonList.observe(this) { pokemonList ->
            binding.rvPokemons.adapter = PokemonAdapter(pokemonList) { pokemon ->
                val intent = Intent(this, PokemonDetailActivity::class.java)
                intent.putExtra("pokemon_id", pokemon.id)
                startActivity(intent)
            }
            binding.layoutError.visibility = View.GONE
        }

        //MENSAJE ERROR
        pokemonViewModel.errorMessage.observe(this) { errorMessage ->
            binding.tvErrorMessage.text = errorMessage
            binding.layoutError.visibility = View.VISIBLE
        }

        // BARRA DE CARGA
        pokemonViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBarLoading.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }

        //ESTADO VACIO
        pokemonViewModel.isEmpty.observe(this) { isEmpty ->
            binding.tvEmptyMessage.visibility =
                if (isEmpty) View.VISIBLE else View.GONE
        }

        pokemonViewModel.canGoPrevious.observe(this) { canGoPrevious ->
            binding.btnPreviousPage.isEnabled = canGoPrevious
        }

        pokemonViewModel.canGoNext.observe(this) { canGoNext ->
            binding.btnNextPage.isEnabled = canGoNext
        }

        //BOTON REINTENTAR
        binding.btnRetry.setOnClickListener {
            binding.layoutError.visibility = View.GONE
            pokemonViewModel.getPokemons()
        }

        binding.btnNextPage.setOnClickListener {
            pokemonViewModel.loadNextPage()
        }

        binding.btnPreviousPage.setOnClickListener {
            pokemonViewModel.loadPreviousPage()
        }


        pokemonViewModel.getPokemons()
    }
}
