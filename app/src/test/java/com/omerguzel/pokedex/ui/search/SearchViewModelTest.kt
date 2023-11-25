package com.omerguzel.pokedex.ui.search

import com.omerguzel.pokedex.TestCoroutineRule
import com.omerguzel.pokedex.data.remote.network.response.PokemonList
import com.omerguzel.pokedex.data.remote.network.response.Result
import com.omerguzel.pokedex.domain.model.PokemonUIItem
import com.omerguzel.pokedex.domain.model.PokemonUIList
import com.omerguzel.pokedex.domain.usecase.FetchPokemonDetailsUseCase
import com.omerguzel.pokedex.domain.usecase.FetchPokemonListUseCase
import com.omerguzel.pokedex.runTestAndCleanUp
import com.omerguzel.pokedex.ui.search.model.SearchUIState
import com.omerguzel.pokedex.util.AggregatedResource
import com.omerguzel.pokedex.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: SearchViewModel

    @MockK
    lateinit var fetchPokemonListUseCase: FetchPokemonListUseCase

    @MockK
    lateinit var fetchPokemonDetailsUseCase: FetchPokemonDetailsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery { fetchPokemonDetailsUseCase(any(), any()) } returns
                flowOf(AggregatedResource.Success(buildPokemonUIList()))

        viewModel = SearchViewModel(fetchPokemonListUseCase, fetchPokemonDetailsUseCase)
    }

    @Test
    fun `when fetchPokemonList is called, uiState is updated correctly`() = runTestAndCleanUp {
       //Given
        coEvery { fetchPokemonListUseCase(any(), any()) } returns
                flowOf(Resource.Success(buildPokemonList()))

        // When
        viewModel.fetchPokemonList(18, 0)


        val actualValue = viewModel.uiState.value.peekContent()
        val expectedValue = SearchUIState(
            isPagingLoadingVisible = false,
            isInSearchMode = false,
            isSortedByNumber = true
        )

        // Then
        assertEquals(expectedValue, actualValue)
    }

    private fun buildPokemonUIList()=  PokemonUIList(count = 100, next = "nextUrl", previous = "prevUrl", results = buildPokemonUIItemList())
    private fun buildPokemonUIItemList() = listOf(PokemonUIItem(), PokemonUIItem())
    private fun buildPokemonList() = PokemonList(count = 100, next = "nextUrl", previous = "prevUrl", results = buildResults())
    private fun buildResults() =  listOf(Result(name = "x", url ="y"), Result(name = "x", url ="y"))

}





