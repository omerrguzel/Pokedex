package com.omerguzel.pokedex.ui.search.model

import com.google.common.truth.Truth
import com.omerguzel.pokedex.R
import com.omerguzel.pokedex.ui.search.SortType
import org.junit.Test

class SearchUIStateTest {

    @Test
    fun `given isInSearchMode, when isPokemonRecyclerViewVisible called, then should return false`() {
        // Given
        val isInSearchMode = true
        val uiState= SearchUIState(isInSearchMode=isInSearchMode)

        // When
        val actualResult = uiState.isPokemonRecyclerViewVisible()

        // Then
        Truth.assertThat(actualResult).isFalse()
    }

    @Test
    fun `given is not inSearchMode, when isPokemonRecyclerViewVisible called, then should return true`() {
        // Given
        val isInSearchMode = false
        val uiState= SearchUIState(isInSearchMode=isInSearchMode)

        // When
        val actualResult = uiState.isPokemonRecyclerViewVisible()

        // Then
        Truth.assertThat(actualResult).isTrue()
    }


    @Test
    fun `given isInSearchMode, when isSearchRecyclerViewVisible called, then should return true`() {
        // Given
        val isInSearchMode = true
        val uiState= SearchUIState(isInSearchMode=isInSearchMode)

        // When
        val actualResult = uiState.isSearchRecyclerViewVisible()

        // Then
        Truth.assertThat(actualResult).isTrue()
    }

    @Test
    fun `given is not inSearchMode, when isSearchRecyclerViewVisible called, then should return false`() {
        // Given
        val isInSearchMode = false
        val uiState= SearchUIState(isInSearchMode=isInSearchMode)

        // When
        val actualResult = uiState.isSearchRecyclerViewVisible()

        // Then
        Truth.assertThat(actualResult).isFalse()
    }

    @Test
    fun `given isSortedByNumber, when getBtnSortIcon called, then should return expected res`(){
        // Given
        val isSortedByNumber = true
        val uiState = SearchUIState(isSortedByNumber=isSortedByNumber)

        // When
        val actualResult = uiState.getBtnSortIcon()
        val expectedResult = R.drawable.ic_tag

        //Then
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `given isSortedByName, when getBtnSortIcon called, then should return expected res`(){
        // Given
        val isSortedByNumber = false
        val uiState = SearchUIState(isSortedByNumber=isSortedByNumber)

        // When
        val actualResult = uiState.getBtnSortIcon()
        val expectedResult = R.drawable.ic_letter_sort

        //Then
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `given isSortedByNumber, when getCurrentSortOption called, then should return expected type`(){
        // Given
        val isSortedByNumber = true
        val uiState = SearchUIState(isSortedByNumber=isSortedByNumber)

        // When
        val actualResult = uiState.getCurrentSortOption()
        val expectedResult = SortType.NUMBER

        //Then
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `given isSortedByName, when getCurrentSortOption called, then should return expected type`(){
        // Given
        val isSortedByNumber = false
        val uiState = SearchUIState(isSortedByNumber=isSortedByNumber)

        // When
        val actualResult = uiState.getCurrentSortOption()
        val expectedResult = SortType.NAME

        //Then
        Truth.assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `given isViewNoResultVisible, when isNoResultViewVisible called, then should return true`() {
        // Given
        val isViewNoResultVisible = true
        val uiState= SearchUIState(isViewNoResultVisible=isViewNoResultVisible)

        // When
        val actualResult = uiState.isNoResultViewVisible()

        // Then
        Truth.assertThat(actualResult).isTrue()
    }

    @Test
    fun `given is not isViewNoResultVisible, when isNoResultViewVisible called, then should return false`() {
        // Given
        val isViewNoResultVisible = false
        val uiState= SearchUIState(isViewNoResultVisible=isViewNoResultVisible)

        // When
        val actualResult = uiState.isNoResultViewVisible()

        // Then
        Truth.assertThat(actualResult).isFalse()
    }
}
