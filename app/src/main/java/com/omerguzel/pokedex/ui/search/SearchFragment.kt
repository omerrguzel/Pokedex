package com.omerguzel.pokedex.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.omerguzel.pokedex.R
import com.omerguzel.pokedex.databinding.FragmentSearchBinding
import com.omerguzel.pokedex.domain.model.PokemonUIItem
import com.omerguzel.pokedex.extensions.initPagingLoadingBar
import com.omerguzel.pokedex.extensions.visibleOrGone
import com.omerguzel.pokedex.ui.base.BaseFragment
import com.omerguzel.pokedex.ui.base.StatusBarColorChanger
import com.omerguzel.pokedex.ui.search.model.PokemonListUIState
import com.omerguzel.pokedex.ui.search.model.SearchUIEvents
import com.omerguzel.pokedex.ui.search.model.SearchUIState
import com.omerguzel.pokedex.util.collectLatestEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tr.com.allianz.digitall.extensions.viewbinding.viewBinding
import tr.com.allianz.digitall.util.list.RecyclerViewPaginator

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    private var currentSortOption: SortType = SortType.NUMBER

    private val defaultAdapter: PokemonAdapter = PokemonAdapter()
    private val searchAdapter: PokemonAdapter = PokemonAdapter()

    private var isInSearchMode: Boolean = false

    private val statusBarColorChanger: StatusBarColorChanger
        get() = requireActivity() as StatusBarColorChanger

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initListeners()
        observePokemonList()
        observeSearchPokemonList()
        observeUIState()
    }

    private fun initUI() {
        binding.paginationProgressBar.initPagingLoadingBar(requireContext())
        statusBarColorChanger.changeStatusBarColor(R.color.primary)
        initSearch()
        initRVs()
    }

    private fun initListeners() {
        binding.btnSort.setOnClickListener {
            val sortDialogView = SortDialog(
                requireContext(),
                defaultOption = currentSortOption,
                onSortOptionChanged = {
                    changeSortType(it)
                }
            )
            sortDialogView.showDialog()
        }

        defaultAdapter.itemSelectListener = {
            navToDetail(it)
        }
        searchAdapter.itemSelectListener = {
            navToDetail(it)
        }
    }

    private fun initRVs() {
        with(binding) {
            rvPokemon.adapter = defaultAdapter
            rvSearchPokemon.adapter = searchAdapter
            rvPokemon.visibleOrGone(isInSearchMode.not())
            rvSearchPokemon.visibleOrGone(isInSearchMode)

            val paginator = object : RecyclerViewPaginator(binding.rvPokemon) {
                override fun loadMore(start: Int, count: Int) {
                    viewModel.handleUIEvents(SearchUIEvents.OnLoadMore)
                }
            }
            rvPokemon.addOnScrollListener(paginator)
        }
    }

    private fun observePokemonList() {
        viewModel.pokemonDetailsState.collectLatestEvent(this@SearchFragment) { state ->
            when (state) {
                is PokemonListUIState.Error -> {
                    //TODO
                }

                is PokemonListUIState.Loading -> Unit
                is PokemonListUIState.Success -> {
                    state.data.results?.let {
                        defaultAdapter.appendData(it)
                    }
                }
            }
        }
    }

    private fun observeSearchPokemonList() {
        viewModel.pokemonSearchDetailsState.collectLatestEvent(this@SearchFragment) { state ->
            when (state) {
                is PokemonListUIState.Error -> {
                    //TODO
                }

                is PokemonListUIState.Loading -> Unit
                is PokemonListUIState.Success -> {
                    state.data.results?.let {
                        searchAdapter.submitData(it)
                    }
                }
            }
        }
    }

    private fun observeUIState() {
        viewModel.uiState.collectLatestEvent(this@SearchFragment) { state ->
            handleUIStateChange(state)
        }
    }

    private fun handleUIStateChange(state: SearchUIState) {
        with(binding) {
            paginationProgressBar.visibleOrGone(state.isPagingLoadingVisible)
            isInSearchMode = state.isInSearchMode
            rvPokemon.visibleOrGone(state.isPokemonRecyclerViewVisible())
            rvSearchPokemon.visibleOrGone(state.isSearchRecyclerViewVisible())
            defaultAdapter.sortByType(state.isSortedByNumber)
            searchAdapter.sortByType(state.isSortedByNumber)
            currentSortOption = state.getCurrentSortOption()
            btnSort.setIconResource(state.getBtnSortIcon())
            viewNoPokemon.root.visibleOrGone(state.isNoResultViewVisible())
        }
    }


    private fun changeSortType(sortType: SortType) {
        viewModel.handleUIEvents(SearchUIEvents.OnSortTypeChanged(sortType))
    }

    private fun initSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                handleQueryText(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handleQueryText(newText)
                return true
            }
        })
    }

    private fun handleQueryText(query: String?) {
        query?.let {
            lifecycleScope.launch {
                delay(500)
            }
            viewModel.handleUIEvents(SearchUIEvents.OnSearchQuerySubmitted(query))
        }
    }

    private fun navToDetail(pokemonUIItem: PokemonUIItem) {
        nav(SearchFragmentDirections.actionSearchFragmentToDetailFragment(pokemonUIItem))
    }
}
