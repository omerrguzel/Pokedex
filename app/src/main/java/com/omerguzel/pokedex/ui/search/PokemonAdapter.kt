package com.omerguzel.pokedex.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.omerguzel.pokedex.R
import com.omerguzel.pokedex.databinding.ItemPokemonBinding
import com.omerguzel.pokedex.domain.model.PokemonUIItem
import com.omerguzel.pokedex.extensions.capitalizeFirstLetter
import com.omerguzel.pokedex.extensions.showImage

class PokemonAdapter(
    var itemSelectListener: ((pokemonUIItem: PokemonUIItem) -> Unit)? = null,
    ) : ListAdapter<PokemonUIItem, PokemonAdapter.PokemonViewHolder>(DIFF_CALLBACK) {

    private var pokemonList = mutableListOf<PokemonUIItem>()

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PokemonUIItem>() {
            override fun areItemsTheSame(oldItem: PokemonUIItem, newItem: PokemonUIItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PokemonUIItem,
                newItem: PokemonUIItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitData(pokemonItems: List<PokemonUIItem>) {
        pokemonList.addAll(pokemonItems)
        pokemonList = pokemonList.distinct().toMutableList()
        submitList(pokemonList.toList())
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemonItem = getItem(position)
        holder.bind(pokemonItem)
    }

    private fun getItemSafe(position: Int): PokemonUIItem? {
        return if (position != RecyclerView.NO_POSITION) {
            getItem(position)
        } else null
    }

    fun clearList() {
        pokemonList.clear()
        submitList(pokemonList.toList())
    }

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {

        fun bind(pokemon: PokemonUIItem) {
            with(binding){
                textId.text= itemView.rootView.context.getString(R.string.pokemon_id,pokemon.id.toString())
                textName.text=pokemon.name.capitalizeFirstLetter()
                image.showImage(pokemon.image)
                root.setOnClickListener{
                    itemSelectListener?.invoke(pokemon)
                }
            }
        }
    }
}
