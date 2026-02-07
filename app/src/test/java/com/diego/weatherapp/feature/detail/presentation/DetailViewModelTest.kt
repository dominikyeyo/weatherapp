package com.diego.weatherapp.feature.detail.presentation

import com.diego.weatherapp.domain.model.CurrentWeather
import com.diego.weatherapp.domain.model.Forecast
import com.diego.weatherapp.domain.model.ForecastDay
import com.diego.weatherapp.domain.usecase.GetForecastUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val getForecastUseCase: GetForecastUseCase = mockk(relaxed = true)
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = DetailViewModel(getForecastUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadForecast emits Loading then Success`() = runTest {
        val query = "4.6,-74.08"
        val forecast = fakeForecast()

        coEvery { getForecastUseCase(query, days = 3) } returns forecast

        viewModel.loadForecast(query)

        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertTrue(state is DetailUiState.Success)
        coVerify(exactly = 1) { getForecastUseCase(query, days = 3) }
    }

    @Test
    fun `loadForecast with same query does not refetch after Success`() = runTest {
        val query = "4.6,-74.08"
        val forecast = fakeForecast()

        coEvery { getForecastUseCase(query, days = 3) } returns forecast

        viewModel.loadForecast(query)
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.loadForecast(query)
        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertTrue(state is DetailUiState.Success)
        coVerify(exactly = 1) { getForecastUseCase(query, days = 3) }
    }

    @Test
    fun `loadForecast force true refetches even if already Success`() = runTest {
        val query = "4.6,-74.08"
        val forecast = fakeForecast()

        coEvery { getForecastUseCase(query, days = 3) } returns forecast

        viewModel.loadForecast(query)
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.loadForecast(query, force = true)
        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertTrue(state is DetailUiState.Success)
        coVerify(exactly = 2) { getForecastUseCase(query, days = 3) }
    }

    @Test
    fun `loadForecast maps unable to resolve host to friendly message`() = runTest {
        val query = "4.6,-74.08"

        coEvery { getForecastUseCase(query, days = 3) } throws IOException(
            "Unable to resolve host \"api.weatherapi.com\": No address associated with hostname"
        )

        viewModel.loadForecast(query)
        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertTrue(state is DetailUiState.Error)
        state as DetailUiState.Error

        assertTrue(state.message.contains("Sin conexión", ignoreCase = true))
    }

    @Test
    fun `loadForecast emits Loading before Success`() = runTest {
        val query = "4.6,-74.08"
        val forecast = fakeForecast()

        val gate = CompletableDeferred<Forecast>()

        coEvery { getForecastUseCase(query, days = 3) } coAnswers { gate.await() }

        viewModel.loadForecast(query)

        dispatcher.scheduler.runCurrent()

        assertTrue(viewModel.uiState.value is DetailUiState.Loading)

        gate.complete(forecast)
        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is DetailUiState.Success)
    }

    @Test
    fun `loadForecast maps generic error to a message`() = runTest {
        val query = "4.6,-74.08"

        coEvery { getForecastUseCase(query, days = 3) } throws RuntimeException("Boom")

        viewModel.loadForecast(query)
        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertTrue(state is DetailUiState.Error)
        state as DetailUiState.Error

        assertTrue(state.message.contains("Boom", ignoreCase = true))
    }

    private fun fakeForecast(): Forecast {
        return Forecast(
            locationName = "Bogota",
            country = "Colombia",
            current = CurrentWeather(
                tempC = 19.0,
                conditionText = "Mist",
                conditionIconUrl = "https://cdn.weatherapi.com/weather/64x64/day/143.png"
            ),
            days = listOf(
                ForecastDay(
                    date = "2026-02-07",
                    avgTempC = 13.0,
                    conditionText = "Moderate rain",
                    conditionIconUrl = "https://cdn.weatherapi.com/weather/64x64/day/302.png"
                ),
                ForecastDay(
                    date = "2026-02-08",
                    avgTempC = 12.0,
                    conditionText = "Patchy rain nearby",
                    conditionIconUrl = "https://cdn.weatherapi.com/weather/64x64/day/176.png"
                ),
                ForecastDay(
                    date = "2026-02-09",
                    avgTempC = 13.0,
                    conditionText = "Patchy rain nearby",
                    conditionIconUrl = "https://cdn.weatherapi.com/weather/64x64/day/176.png"
                ),
            )
        )
    }
}
