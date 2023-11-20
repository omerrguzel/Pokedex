package com.omerguzel.pokedex.ui.search

import androidx.lifecycle.viewModelScope
import com.omerguzel.pokedex.data.remote.network.response.PokemonList
import com.omerguzel.pokedex.domain.model.PokemonUIList
import com.omerguzel.pokedex.domain.usecase.FetchPokemonDetailsUseCase
import com.omerguzel.pokedex.domain.usecase.FetchPokemonListUseCase
import com.omerguzel.pokedex.ui.base.BaseViewModel
import com.omerguzel.pokedex.ui.search.model.SearchUIEvents
import com.omerguzel.pokedex.ui.search.model.SearchUIStates
import com.omerguzel.pokedex.util.AggregatedResource
import com.omerguzel.pokedex.util.Event
import com.omerguzel.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val fetchPokemonListUseCase: FetchPokemonListUseCase,
    private val fetchPokemonDetailsUseCase: FetchPokemonDetailsUseCase
) : BaseViewModel() {

    private var isLoadingMore = AtomicBoolean(false)
    private var isLastPage = AtomicBoolean(false)
    private var offset = 0

    private val _uiState = MutableStateFlow(Event(SearchUIStates()))
    val uiState = _uiState.asStateFlow()

    private val _pokemonListState =
        MutableStateFlow<Event<Resource<PokemonList?>?>>(Event(null))

    private val _pokemonDetailsState =
        MutableStateFlow<Event<AggregatedResource<PokemonUIList>?>>(Event(null))
    val pokemonDetailsState = _pokemonDetailsState.asStateFlow()


    init {
        fetchPokemonList(18, 0)
    }

    private fun fetchPokemonList(limit: Int, offset: Int) {
        viewModelScope.launch {
            _uiState.emit(Event(SearchUIStates(isPagingLoadingVisible = true)))
            fetchPokemonListUseCase(limit, offset).collect { resource ->
                _pokemonListState.emit(Event(resource))
                when (resource) {
                    is Resource.Error -> {
                        isLoadingMore.set(false)
                        isLastPage.set(false)
                    }

                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        resource.data?.let {
                            fetchPokemonDetails(it)
                        }
                        resource.data?.results?.let {
                            if (it.isEmpty() || it.size < limit) isLastPage.set(true)
                        }
                        isLoadingMore.set(false)
                    }
                }
            }
        }
    }

    private fun fetchPokemonDetails(pokemonList: PokemonList) {
        viewModelScope.launch {
            val result = fetchPokemonDetailsUseCase(pokemonList)
            _pokemonDetailsState.emit(Event(result))
            if(result != AggregatedResource.Loading) {
                _uiState.emit(Event(SearchUIStates(isPagingLoadingVisible = false)))
            }
        }
    }

    fun handleUIEvents(event: SearchUIEvents<Any>) {
        viewModelScope.launch {
            when (event) {
                SearchUIEvents.OnLoadMore -> {
                    loadMore()
                }
            }
        }
    }

    private fun loadMore() {
        if (isLoadingMore.getAndSet(true) || isLastPage.get()) return
        offset += 9
        fetchPokemonList(18, offset)
    }
}
