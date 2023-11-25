package com.omerguzel.pokedex.ui.search

import androidx.lifecycle.viewModelScope
import com.omerguzel.pokedex.data.remote.network.response.PokemonList
import com.omerguzel.pokedex.domain.model.PokemonUIItem
import com.omerguzel.pokedex.domain.usecase.FetchPokemonDetailsUseCase
import com.omerguzel.pokedex.domain.usecase.FetchPokemonListUseCase
import com.omerguzel.pokedex.ui.base.BaseViewModel
import com.omerguzel.pokedex.ui.search.model.PokemonListUIState
import com.omerguzel.pokedex.ui.search.model.SearchUIEvents
import com.omerguzel.pokedex.ui.search.model.SearchUIState
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
    private var isInSearchMode: Boolean = false
    private var isSortedByNumber: Boolean = true
    private var lastSearchedQuery = ""

    private val _uiState = MutableStateFlow(Event(SearchUIState()))
    val uiState = _uiState.asStateFlow()


    private val _pokemonListState =
        MutableStateFlow<Event<Resource<PokemonList?>?>>(Event(null))


    private val _pokemonSearchListState =
        MutableStateFlow<Event<Resource<PokemonList?>?>>(Event(null))


    private val _pokemonDetailsState = MutableStateFlow<Event<PokemonListUIState?>>(Event(null))
    val pokemonDetailsState = _pokemonDetailsState.asStateFlow()

    private val _pokemonSearchDetailsState =
        MutableStateFlow<Event<PokemonListUIState?>>(Event(null))
    val pokemonSearchDetailsState = _pokemonSearchDetailsState.asStateFlow()

    init {
        fetchPokemonList(18, 0)
    }

    private fun updateUIState(update: (currentState: SearchUIState) -> SearchUIState) {
        val currentState = _uiState.value.peekContent()
        val newState = update(currentState)
        viewModelScope.launch {
            _uiState.emit(Event(newState))
        }
    }

    fun fetchPokemonList(limit: Int, offset: Int) {
        viewModelScope.launch {
            fetchPokemonListUseCase(limit, offset).collect { resource ->
                _pokemonListState.emit(Event(resource))
                when (resource) {
                    is Resource.Error -> {
                        isLoadingMore.set(false)
                        isLastPage.set(false)
                        updateUIState {
                            it.copy(
                                isPagingLoadingVisible = false,
                                isSortedByNumber = isSortedByNumber
                            )
                        }
                    }

                    is Resource.Loading -> {
                        updateUIState {
                            it.copy(
                                isPagingLoadingVisible = true,
                                isSortedByNumber = isSortedByNumber
                            )
                        }
                    }

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

    private fun fetchAllPokemonList(filterQuery: String) {
        viewModelScope.launch {
            fetchPokemonListUseCase(10000, 0).collect { resource ->
                _pokemonSearchListState.emit(Event(resource))
                when (resource) {
                    is Resource.Error -> {
                        //TODO
                    }

                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        resource.data?.let {
                            fetchPokemonDetails(it, filterQuery)
                        }
                    }
                }
            }
        }
    }

    private fun fetchPokemonDetails(pokemonList: PokemonList, filterQuery: String? = null) {
        viewModelScope.launch {
            fetchPokemonDetailsUseCase(pokemonList, filterQuery).collect { aggregatedResource ->
                when (aggregatedResource) {
                    is AggregatedResource.Loading -> {
                        updateUIState {
                            it.copy(
                                isPagingLoadingVisible = true,
                                isSortedByNumber = isSortedByNumber,
                                isViewNoResultVisible = false
                            )
                        }
                        decideListState().emit(Event(PokemonListUIState.Loading))
                    }

                    is AggregatedResource.Error -> {
                        updateUIState {
                            it.copy(
                                isPagingLoadingVisible = false,
                                isSortedByNumber = isSortedByNumber,
                                isViewNoResultVisible = false
                            )
                        }
                        decideListState().emit(Event(PokemonListUIState.Error(aggregatedResource.message)))
                    }

                    is AggregatedResource.Success -> {
                        updateUIState {
                            it.copy(
                                isPagingLoadingVisible = false,
                                isSortedByNumber = isSortedByNumber,
                                isViewNoResultVisible = decideNoResultVisibility(aggregatedResource.data.results)
                            )
                        }
                        decideListState().emit(
                            Event(
                                PokemonListUIState.Success(
                                    aggregatedResource.data
                                )
                            )
                        )
                    }

                    is AggregatedResource.PartialSuccess -> {
                        updateUIState {
                            it.copy(
                                isPagingLoadingVisible = false,
                                isSortedByNumber = isSortedByNumber,
                                isViewNoResultVisible = decideNoResultVisibility(aggregatedResource.data.results)
                            )
                        }
                        decideListState().emit(
                            Event(
                                PokemonListUIState.Success(
                                    aggregatedResource.data
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    private fun decideListState(): MutableStateFlow<Event<PokemonListUIState?>> {
        return if (isInSearchMode) {
            _pokemonSearchDetailsState
        } else _pokemonDetailsState
    }

    private fun decideNoResultVisibility(data: List<PokemonUIItem>?): Boolean {
        data.let {
            return isInSearchMode && data?.isEmpty() == true
        }
    }

    fun handleUIEvents(event: SearchUIEvents<Any>) {
        viewModelScope.launch {
            when (event) {
                SearchUIEvents.OnLoadMore -> {
                    loadMore()
                }

                is SearchUIEvents.OnSearchQuerySubmitted -> {
                    handleSearch(event)
                }

                is SearchUIEvents.OnSortTypeChanged -> {
                    handleSorting(event)
                }
            }
        }
    }

    private fun handleSearch(event: SearchUIEvents.OnSearchQuerySubmitted) {
        viewModelScope.launch {
            isInSearchMode = event.query.isNotEmpty()
            if (isInSearchMode && lastSearchedQuery != event.query && event.query.length >= 3) {
                lastSearchedQuery = event.query
                fetchAllPokemonList(event.query)
            }
            updateUIState { currentState ->
                currentState.copy(
                    isPagingLoadingVisible = false,
                    isInSearchMode = isInSearchMode,
                    isSortedByNumber = isSortedByNumber,
                    isViewNoResultVisible = false
                )
            }
        }
    }

    private fun handleSorting(event: SearchUIEvents.OnSortTypeChanged) {
        isSortedByNumber = event.sortType == SortType.NUMBER
        updateUIState { currentState ->
            currentState.copy(isSortedByNumber = isSortedByNumber)
        }
    }

    private fun loadMore() {
        if (isLoadingMore.getAndSet(true) || isLastPage.get()) return
        offset += 9
        fetchPokemonList(18, offset)
    }
}
