package com.omerguzel.pokedex.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.omerguzel.pokedex.R
import com.omerguzel.pokedex.databinding.FragmentDetailBinding
import com.omerguzel.pokedex.domain.model.PokemonUIItem
import com.omerguzel.pokedex.extensions.capitalizeFirstLetter
import com.omerguzel.pokedex.extensions.color
import com.omerguzel.pokedex.extensions.drawable
import com.omerguzel.pokedex.extensions.formatHeight
import com.omerguzel.pokedex.extensions.formatWeight
import com.omerguzel.pokedex.extensions.replaceSpecialCharactersWithSpace
import com.omerguzel.pokedex.extensions.showImage
import com.omerguzel.pokedex.ui.base.BaseFragment
import com.omerguzel.pokedex.ui.base.StatusBarColorChanger
import com.omerguzel.pokedex.util.Resource
import com.omerguzel.pokedex.util.collectLatestEvent
import dagger.hilt.android.AndroidEntryPoint
import tr.com.allianz.digitall.extensions.viewbinding.viewBinding

@AndroidEntryPoint
class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val viewModel by viewModels<DetailViewModel>()

    private val args: DetailFragmentArgs by navArgs()

    private val typeAdapter: TypeAdapter = TypeAdapter()
    private val statAdapter: StatAdapter = StatAdapter()

    private val statusBarColorChanger : StatusBarColorChanger
        get()= requireActivity() as StatusBarColorChanger

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemon = args.pokemonItem
        setScreenThemeColor(pokemon)
        observePokemonDescription()
        with(binding) {
            rvType.adapter = typeAdapter
            rvStats.adapter = statAdapter
            typeAdapter.submitList(pokemon.types)
            statAdapter.submitList(pokemon.stats)
            Log.d("mylog", "Pokemon $pokemon")
            textTitle.text = pokemon.name.capitalizeFirstLetter()
            textId.text = getString(R.string.pokemon_id, pokemon.id.toString())
            imagePokemon.showImage(args.pokemonItem.image)
            viewWeight.textValue.text = pokemon.weight?.formatWeight(requireContext())
            viewHeight.textValue.text = pokemon.height?.formatHeight(requireContext())
            viewHeight.textTitle.text = getString(R.string.height)
            viewHeight.imageIcon.setImageDrawable(requireContext().drawable(R.drawable.ic_height))
            val abilities = pokemon.abilities
            viewAbilities.textFirstAbility.text =
                abilities.getOrNull(0)?.capitalizeFirstLetter() ?: "-"
            viewAbilities.textSecondAbility.text =
                abilities.getOrNull(1)?.capitalizeFirstLetter() ?: ""
            btnBack.setOnClickListener { navBack() }
        }
    }

    private fun setScreenThemeColor(pokemonUIItem: PokemonUIItem) {
        with(binding) {
            val pokemonType = pokemonUIItem.types?.get(0)?.let { PokemonType.fromString(it) }
            val colorRes = pokemonType?.colorRes ?: R.color.wireframe_grayscale
            statusBarColorChanger.changeStatusBarColor(colorRes)
            root.setBackgroundColor(requireContext().color(colorRes))
            textAbout.setTextColor(requireContext().color(colorRes))
            textBaseStats.setTextColor(requireContext().color(colorRes))
            statAdapter.setProgressBarColor(colorRes)
        }
    }

    private fun observePokemonDescription() {
        viewModel.pokemonDescription.collectLatestEvent(this@DetailFragment) { state ->
            when (state) {
                is Resource.Error -> TODO()
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    state.data?.descriptionEntries?.get(0)?.text.let {
                        binding.textDescription.text = it?.replaceSpecialCharactersWithSpace() ?: ""
                    }
                }
            }
        }
    }
}
