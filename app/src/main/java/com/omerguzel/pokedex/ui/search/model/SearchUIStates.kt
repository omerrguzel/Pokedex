package com.omerguzel.pokedex.ui.search.model

data class SearchUIStates(
    val isPagingLoadingVisible : Boolean = true,
    val isInSearchMode: Boolean = false,
    val isSortedByNumber: Boolean = true
)
