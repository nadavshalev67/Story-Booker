package com.example.storyapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PokemonEntity::class,
        TypeEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}