package com.example.storyapplication.data

import com.example.storyapplication.room.PokemonEntity
import com.example.storyapplication.room.TypeEntity

object PokemonMockData {

    fun getAllEntities() = listOf(
        getFireEntities(),
        getWaterEntities(),
        getElectricEntities(),
        getGrassEntities(),
    )

    private fun getFireEntities(): Pair<TypeEntity, List<PokemonEntity>> {
        val type = "Fire"
        return Pair(
            first = TypeEntity(
                index = 0,
                type = type,
                imageUrl = "https://www.giantbomb.com/a/uploads/scale_medium/16/164924/3083931-8746743194-flat%2C.jpg"
            ),
            second = listOf(
                PokemonEntity(
                    type = type,
                    index = 0,
                    imageUrl = "https://img.pokemondb.net/artwork/large/charmander.jpg",
                    pokemonName = "Charmander",
                ),
                PokemonEntity(
                    type = type,
                    index = 1,
                    imageUrl = "https://img.pokemondb.net/artwork/large/charmeleon.jpg",
                    pokemonName = "Charmeleon",
                ),
                PokemonEntity(
                    type = type,
                    index = 2,
                    imageUrl = "https://img.pokemondb.net/artwork/large/charizard.jpg",
                    pokemonName = "Charizard",
                ),
            )
        )
    }

    private fun getWaterEntities(): Pair<TypeEntity, List<PokemonEntity>> {
        val type = "Water"
        return Pair(
            first = TypeEntity(
                index = 1,
                type = type,
                imageUrl = "https://i.pinimg.com/originals/c3/61/39/c3613977779d28d1da20e3d814ac1ce0.png"
            ),
            second = listOf(
                PokemonEntity(
                    type = type,
                    index = 0,
                    imageUrl = "https://img.pokemondb.net/artwork/large/squirtle.jpg",
                    pokemonName = "Squirtle",
                ),
                PokemonEntity(
                    type = type,
                    index = 1,
                    imageUrl = "https://img.pokemondb.net/artwork/large/wartortle.jpg",
                    pokemonName = "Wartortle",
                ),
                PokemonEntity(
                    type = type,
                    index = 2,
                    imageUrl = "https://img.pokemondb.net/artwork/large/blastoise.jpg",
                    pokemonName = "Blastoise",
                ),
            )
        )
    }

    private fun getElectricEntities(): Pair<TypeEntity, List<PokemonEntity>> {
        val type = "Electric"
        return Pair(
            first = TypeEntity(
                index = 2,
                type = type,
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTz-WHPYsmO9sJibZXCF5BSbvIXuvAiUe8c7A&s"
            ),
            second = listOf(
                PokemonEntity(
                    type = type,
                    index = 0,
                    imageUrl = "https://img.pokemondb.net/artwork/large/pichu.jpg",
                    pokemonName = "Pichu",
                ),
                PokemonEntity(
                    type = type,
                    index = 1,
                    imageUrl = "https://img.pokemondb.net/artwork/large/pikachu.jpg",
                    pokemonName = "Pikachu",
                ),
                PokemonEntity(
                    type = type,
                    index = 2,
                    imageUrl = "https://img.pokemondb.net/artwork/large/raichu.jpg",
                    pokemonName = "Raichu",
                ),
            )
        )
    }

    private fun getGrassEntities(): Pair<TypeEntity, List<PokemonEntity>> {
        val type = "Grass"
        return Pair(
            first = TypeEntity(
                index = 3,
                type = type,
                imageUrl = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/e8ddc4da-23dd-4502-b65b-378c9cfe5efa/dffvl0s-d443a3b4-fa4e-47a6-99b4-d2a769785614.png/v1/fill/w_894,h_894/grass_type_symbol_galar_by_jormxdos_dffvl0s-pre.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MTI4MCIsInBhdGgiOiJcL2ZcL2U4ZGRjNGRhLTIzZGQtNDUwMi1iNjViLTM3OGM5Y2ZlNWVmYVwvZGZmdmwwcy1kNDQzYTNiNC1mYTRlLTQ3YTYtOTliNC1kMmE3Njk3ODU2MTQucG5nIiwid2lkdGgiOiI8PTEyODAifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.6-S1a0_YYhlP6eXW5QqrJk4jtv6b5a3MRuugxqhJ6EA"
            ),
            second = listOf(
                PokemonEntity(
                    type = type,
                    index = 0,
                    imageUrl = "https://img.pokemondb.net/artwork/large/bulbasaur.jpg",
                    pokemonName = "Bulbasaur",
                ),
                PokemonEntity(
                    type = type,
                    index = 1,
                    imageUrl = "https://img.pokemondb.net/artwork/large/ivysaur.jpg",
                    pokemonName = "Ivysaur",
                ),
                PokemonEntity(
                    type = type,
                    index = 2,
                    imageUrl = "https://img.pokemondb.net/artwork/large/venusaur.jpg",
                    pokemonName = "Venusaur",
                ),
            )
        )
    }

}