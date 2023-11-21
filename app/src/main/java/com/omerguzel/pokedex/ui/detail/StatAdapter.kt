package com.omerguzel.pokedex.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.omerguzel.pokedex.R
import com.omerguzel.pokedex.databinding.ItemBaseStatsBinding
import com.omerguzel.pokedex.extensions.color
import com.omerguzel.pokedex.extensions.colorWithReducedAlpha

class StatAdapter() : ListAdapter<Pair<String?, Int?>, StatAdapter.StatViewHolder>(DIFF_CALLBACK) {

    private var typeColor = R.color.wireframe_grayscale

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pair<String?, Int?>>() {
            override fun areItemsTheSame(
                oldItem: Pair<String?, Int?>,
                newItem: Pair<String?, Int?>
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Pair<String?, Int?>, newItem: Pair<String?, Int?>
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): StatViewHolder {
        val binding =
            ItemBaseStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        val typeItem = getItem(position)
        holder.bind(typeItem)
    }

    fun setProgressBarColor(@ColorRes color: Int) {
        typeColor = color
    }

    inner class StatViewHolder(private val binding: ItemBaseStatsBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(stat: Pair<String?, Int?>) {
            with(binding) {
                stat.first?.let {
                    val statType = StatType.fromString(it)
                    val stringResId = statType?.stringRes ?: R.string.unknown_stat
                    textHp.setText(stringResId)
                }
                textHp.setTextColor(itemView.context.color(typeColor))
                stat.second?.let { progressBar.setProgress(it, true) }
                progressBar.setIndicatorColor(itemView.context.color(typeColor))
                val reducedTypeColor = itemView.context.colorWithReducedAlpha(typeColor)
                progressBar.trackColor = reducedTypeColor
            }
        }
    }
}
