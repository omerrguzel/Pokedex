package com.omerguzel.pokedex.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.omerguzel.pokedex.R
import com.omerguzel.pokedex.databinding.FragmentSearchBinding
import com.omerguzel.pokedex.ui.base.BaseFragment
import com.omerguzel.pokedex.util.AggregatedResource
import com.omerguzel.pokedex.util.collectLatest
import com.omerguzel.pokedex.util.collectLatestEvent
import dagger.hilt.android.AndroidEntryPoint
import tr.com.allianz.digitall.extensions.viewbinding.viewBinding

@AndroidEntryPoint
class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    private var currentSortOption :SortOption = SortOption.NUMBER

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }

    private fun observePokemonList(){
        viewModel.pokemonDetailsState.collectLatestEvent(this@SearchFragment){state->
            //Handle Loading
            when(state){
                is AggregatedResource.Error -> TODO()
                is AggregatedResource.PartialSuccess -> TODO()
                is AggregatedResource.Success -> {
                    state.data.results?.forEach{
                        Log.d("mylog","name ${it?.image}")
                    }
                }
            }
        }
    }
}
