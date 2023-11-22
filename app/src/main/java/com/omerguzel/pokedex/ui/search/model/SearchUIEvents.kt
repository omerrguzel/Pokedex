package com.omerguzel.pokedex.ui.search.model

import com.omerguzel.pokedex.ui.search.SortType

sealed class SearchUIEvents<out T> {

    data object OnLoadMore : SearchUIEvents<Nothing>()

    data class OnSearchQuerySubmitted(val query: String) : SearchUIEvents<String>()
    data class OnSortTypeChanged(val sortType: SortType) : SearchUIEvents<String>()
}
