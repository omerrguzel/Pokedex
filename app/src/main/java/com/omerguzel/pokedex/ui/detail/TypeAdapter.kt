package com.omerguzel.pokedex.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.omerguzel.pokedex.R
import com.omerguzel.pokedex.databinding.ItemTypeBinding
import com.omerguzel.pokedex.extensions.capitalizeFirstLetter

class TypeAdapter() : ListAdapter<String, TypeAdapter.TypeViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String, newItem: String
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TypeViewHolder {
        val binding = ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        val typeItem = getItem(position)
        holder.bind(typeItem)
    }

    inner class TypeViewHolder(private val binding: ItemTypeBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(type: String) {
            with(binding) {
                binding.chipType.text = type.capitalizeFirstLetter()
                val pokemonType = PokemonType.fromString(type)
                val colorRes = pokemonType?.colorRes ?: R.color.wireframe_grayscale
                chipType.setChipBackgroundColorResource(colorRes)
            }
        }
    }
}
