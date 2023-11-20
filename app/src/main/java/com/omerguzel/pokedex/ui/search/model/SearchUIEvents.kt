package com.omerguzel.pokedex.ui.search.model

sealed class SearchUIEvents<out T> {

    object OnLoadMore : SearchUIEvents<Nothing>()

}
