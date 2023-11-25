package com.omerguzel.pokedex.ui.search.model

import com.omerguzel.pokedex.R
import com.omerguzel.pokedex.ui.search.SortType

data class SearchUIState(
    val isPagingLoadingVisible : Boolean = true,
    val isInSearchMode: Boolean = false,
    val isSortedByNumber: Boolean = true,
    val isViewNoResultVisible : Boolean = false
){

    fun isPokemonRecyclerViewVisible() :Boolean{
        return isInSearchMode.not()
    }
    fun isSearchRecyclerViewVisible() :Boolean{
        return isInSearchMode
    }

    fun getBtnSortIcon():Int{
        return if (isSortedByNumber) R.drawable.ic_tag else R.drawable.ic_letter_sort
    }

    fun getCurrentSortOption() : SortType{
        return if (isSortedByNumber) SortType.NUMBER else SortType.NAME
    }

    fun isNoResultViewVisible() : Boolean{
        return isViewNoResultVisible
    }
}
