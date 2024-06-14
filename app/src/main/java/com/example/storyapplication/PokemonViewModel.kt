package com.example.storyapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapplication.room.PokemonEntity
import com.example.storyapplication.room.TypeWithPokemons
import com.nadavshalev67.storybooker.events.StoryEvents
import com.nadavshalev67.storybooker.thumbnails.ImageSource
import com.nadavshalev67.storybooker.thumbnails.ThumbnailData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class DisplayView {
    data object Thumbnails : DisplayView()
    data object Story : DisplayView()
}

data class StoryUiState(
    val displayView: DisplayView = DisplayView.Thumbnails,
    val indexClicked: Int = 0,
)

class PokemonViewModel(
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {

    private val allPokemonsData: StateFlow<List<TypeWithPokemons>> =
        pokemonRepository.getAllPokemonsData().stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    private val _uiState = MutableStateFlow(StoryUiState())
    val uiState: StateFlow<StoryUiState> = _uiState


    init {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository.refreshList()
        }
    }


    val pokemonsThumbnailFlow: StateFlow<List<ThumbnailData>> =
        allPokemonsData.map {
            it.map { entity ->
                ThumbnailData(
                    imageSource = ImageSource.Url(entity.type.imageUrl),
                    text = entity.type.type,
                    isViewedAll = entity.pokemons.all { it.isDisplayed }
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )


    val pokemonsItemsFlow: StateFlow<Map<Int, List<PokemonEntity>>> =
        allPokemonsData.map {
            it.associate { typeWithPokemons ->
                val index = typeWithPokemons.type.index
                val pokemonItemList = typeWithPokemons.pokemons
                index to pokemonItemList
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyMap()
        )

    fun updateItemLoaded(pokemonItem: PokemonEntity) {
        viewModelScope.launch {
            pokemonRepository.updatePokemonEntity(
                pokemonItem.copy(
                    isLoaded = true,
                )
            )
        }
    }

    fun showThumbnails() {
        _uiState.value = _uiState.value.copy(
            displayView = DisplayView.Thumbnails
        )
    }

    fun onThumbnailClicked(index: Int) {
        _uiState.value = _uiState.value.copy(
            displayView = DisplayView.Story,
            indexClicked = index,
        )
    }

    val storyEventsListener = object : StoryEvents<PokemonEntity>() {
        override fun onOuterPageImpression(outerPageNumber: Int) {
            Log.d(
                "nadav",
                "onOuterPageImpression - [$outerPageNumber]"
            )
        }

        override fun onInnerPageImpression(
            outerPageNumber: Int,
            innerPageNumber: Int,
            item: PokemonEntity,
        ) {
            Log.d(
                "nadav",
                "onInnerPageImpression - [$outerPageNumber-${innerPageNumber}]"
            )
            viewModelScope.launch {
                pokemonRepository.updatePokemonEntity(
                    item.copy(
                        isDisplayed = true,
                    )
                )
            }
        }

        override fun onComplete() {
            Log.d(
                "nadav",
                "onComplete"
            )
            showThumbnails()
        }
    }
}