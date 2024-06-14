package com.example.storyapplication.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nadavshalev67.storybooker.pages.LoadingData

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "index")
    val index: Int,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "is_image_loaded")
    override val isLoaded: Boolean = false,
    @ColumnInfo(name = "pokemon_name")
    val pokemonName: String,
    @ColumnInfo(name = "is_displayed")
    val isDisplayed: Boolean = false,
) : LoadingData