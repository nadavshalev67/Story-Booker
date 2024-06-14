package com.example.storyapplication

import com.example.storyapplication.data.PokemonMockData
import com.example.storyapplication.room.PokemonDao
import com.example.storyapplication.room.PokemonEntity
import com.example.storyapplication.room.TypeWithPokemons
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getAllPokemonsData(): Flow<List<TypeWithPokemons>>
    suspend fun refreshList()
    suspend fun updatePokemonEntity(pokemonItem: PokemonEntity)
}

class PokemonRepositoryImpl(
    private val pokemonDao: PokemonDao,
) : PokemonRepository {
    override fun getAllPokemonsData() = pokemonDao.getTypeWithPokemons()

    override suspend fun refreshList() {
        pokemonDao.withTransaction {
            pokemonDao.deleteAll()
            PokemonMockData.getAllEntities().forEach {
                pokemonDao.insertTypeWithPokemons(
                    it.first,
                    it.second
                )
            }
        }
    }

    override suspend fun updatePokemonEntity(pokemonItem: PokemonEntity) {
        pokemonDao.updatePokemon(
            pokemon = pokemonItem,
        )
    }
}



