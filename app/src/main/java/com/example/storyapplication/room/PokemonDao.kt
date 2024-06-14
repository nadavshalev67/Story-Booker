package com.example.storyapplication.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertType(type: TypeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertPokemons(pokemons: List<PokemonEntity>)

    @Update
    abstract suspend fun updatePokemon(pokemon: PokemonEntity)

    @Transaction
    @Query("SELECT * FROM type_table")
    abstract fun getTypeWithPokemons(): Flow<List<TypeWithPokemons>>

    @Query("DELETE FROM type_table")
    abstract fun deleteAllTypeTable()

    @Query("DELETE FROM pokemon_table")
    abstract fun deleteAllPokemonTable()

    @Transaction
    open suspend fun deleteAll() {
        deleteAllPokemonTable()
        deleteAllTypeTable()
    }

    @Transaction
    open suspend fun insertTypeWithPokemons(type: TypeEntity, pokemons: List<PokemonEntity>) {
        insertType(type)
        insertPokemons(pokemons)
    }

    @Transaction
    open suspend fun withTransaction(tx: suspend () -> Unit) = tx()

}