package com.omerguzel.pokedex.ui.search

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.omerguzel.pokedex.R
import com.omerguzel.pokedex.databinding.FragmentSearchBinding
import com.omerguzel.pokedex.ui.base.BaseFragment
import com.omerguzel.pokedex.ui.search.model.SearchUIEvents
import com.omerguzel.pokedex.util.AggregatedResource
import com.omerguzel.pokedex.util.collectLatestEvent
import dagger.hilt.android.AndroidEntryPoint
import tr.com.allianz.digitall.extensions.viewbinding.viewBinding
import tr.com.allianz.digitall.util.list.RecyclerViewPaginator

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    private var currentSortOption: SortOption = SortOption.NUMBER

    private val adapter: PokemonAdapter = PokemonAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPokemon.adapter = adapter
        binding.btnSort.setOnClickListener {
            val sortDialogView = SortDialog(
                requireContext(),
                defaultOption = currentSortOption,
                onSortOptionChanged = {
                    currentSortOption = it
                }
            )
            sortDialogView.showDialog()
        }
        observePokemonList()

        val paginator = object : RecyclerViewPaginator(binding.rvPokemon) {
            override fun loadMore(start: Int, count: Int) {
                viewModel.handleUIEvents(SearchUIEvents.OnLoadMore)
            }
        }
        binding.rvPokemon.addOnScrollListener(paginator)
        observeUIState()
    }

    fun showLoading(isLoading: Boolean) {
        with(binding.paginationProgressBar){
            visibility = if (isLoading) View.VISIBLE else View.GONE
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.loading_bar)
            val screenWidth = Resources.getSystem().displayMetrics.widthPixels
            drawable?.setBounds(0, 0, screenWidth * 2, height)
            indeterminateDrawable = drawable

            val anim = ObjectAnimator.ofFloat(this, "translationX", -screenWidth.toFloat(), screenWidth.toFloat())
            anim.duration = 800
            anim.repeatMode = ValueAnimator.REVERSE
            anim.repeatCount = ValueAnimator.INFINITE
            anim.start()
        }

    }

    private fun observePokemonList() {
        viewModel.pokemonDetailsState.collectLatestEvent(this@SearchFragment) { state ->
            when (state) {
                is AggregatedResource.Error -> TODO()
                is AggregatedResource.PartialSuccess -> TODO()
                is AggregatedResource.Success -> {
                    state.data.results?.let {
                        adapter.submitData(it)
                    }
                }
                AggregatedResource.Loading -> TODO()
            }
        }
    }

    private fun observeUIState(){
        viewModel.uiState.collectLatestEvent(this@SearchFragment){state->
            with(binding){
                showLoading(state.isPagingLoadingVisible)
            }
        }
    }
}
