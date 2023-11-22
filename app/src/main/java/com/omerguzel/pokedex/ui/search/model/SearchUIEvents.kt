package com.omerguzel.pokedex.ui.search.model

sealed class SearchUIEvents<out T> {

    data object OnLoadMore : SearchUIEvents<Nothing>()

    data class OnSearchQuerySubmitted(val query: String) : SearchUIEvents<String>()
}
