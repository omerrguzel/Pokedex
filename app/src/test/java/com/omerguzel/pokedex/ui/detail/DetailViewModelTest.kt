package com.omerguzel.pokedex.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.omerguzel.pokedex.TestCoroutineRule
import com.omerguzel.pokedex.data.remote.network.response.Species
import com.omerguzel.pokedex.domain.model.PokemonUIItem
import com.omerguzel.pokedex.domain.usecase.FetchPokemonDescriptionUseCase
import com.omerguzel.pokedex.runTestAndCleanUp
import com.omerguzel.pokedex.ui.detail.DetailViewModel.Companion.ARG_DETAIL_POKEMON_ITEM
import com.omerguzel.pokedex.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: DetailViewModel

    @MockK
    lateinit var fetchPokemonDescriptionUseCase: FetchPokemonDescriptionUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when fetchPokemonList is called, uiState is updated correctly`() = runTestAndCleanUp {
        //Given
        viewModel = createViewModel()
        coEvery { fetchPokemonDescriptionUseCase(any()) } returns
                flowOf(Resource.Success(buildSpecies()))


        // When
        viewModel.fetchPokemonDescription("url")


        val actualValue = viewModel.pokemonDescription.value.peekContent()
        val expectedValue = Resource.Success(
            data = Species(
                listOf()
            )
        )

        // Then
        Assert.assertEquals(expectedValue, actualValue)
    }

    private fun createViewModel(argument: PokemonUIItem = PokemonUIItem()):DetailViewModel{
        val savedStateHandle = SavedStateHandle(mapOf(ARG_DETAIL_POKEMON_ITEM to argument))
        return DetailViewModel(
            fetchPokemonDescriptionUseCase,
            savedStateHandle
        )
    }
    private fun buildSpecies()=  Species(listOf())

}
