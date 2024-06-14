package com.example.storyapplication.di

import androidx.room.Room
import com.example.storyapplication.PokemonRepository
import com.example.storyapplication.PokemonRepositoryImpl
import com.example.storyapplication.PokemonViewModel
import com.example.storyapplication.room.AppDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        val builder = Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            name = "pokemon-db",
        )
        builder.build()
    }

    single {
        get<AppDatabase>().pokemonDao()
    }

    single<PokemonRepository> {
        PokemonRepositoryImpl(
            pokemonDao = get(),
        )
    }

    viewModel {
        PokemonViewModel(
            pokemonRepository = get(),
        )
    }


}
