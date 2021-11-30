package com.chan.movie.ui.viewmodel

import com.chan.movie.domain.dto.ItemDto
import com.chan.movie.domain.usecase.MovieSearchUseCaseImpl
import com.chan.movie.ui.main.MovieSearchViewModel
import com.chan.movie.util.InstantExecutorExtension
import com.chan.movie.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class MovieSearchViewModelTest {

    private val useCase: MovieSearchUseCaseImpl = mockk(relaxed = true)
    private lateinit var viewModel: MovieSearchViewModel

    @BeforeEach
    fun setUp() {
        viewModel = MovieSearchViewModel(useCase)
    }

    @Test
    fun `영화 리스트를 불러옵니다`() = runBlocking {

        val mockRes: List<ItemDto> = emptyList()

        val page = 1
        val query = "a"

        coEvery {
            useCase.request(
                page,
                query
            ).getOrNull()?.items
        } returns mockRes

        viewModel.searchMovies(
            query
        )

        assertEquals(
            mockRes,
            viewModel.movies.getOrAwaitValue()
        )
    }
}