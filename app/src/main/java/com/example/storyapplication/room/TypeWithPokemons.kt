package com.example.storyapplication.room

import androidx.room.Embedded
import androidx.room.Relation

data class TypeWithPokemons(
    @Embedded val type: TypeEntity,
    @Relation(
        parentColumn = "type",
        entityColumn = "type"
    )
    val pokemons: List<PokemonEntity>,
)