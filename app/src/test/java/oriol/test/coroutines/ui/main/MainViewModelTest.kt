package oriol.test.coroutines.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import oriol.test.coroutines.ui.main.model.Country
import oriol.test.coroutines.ui.main.repositories.MainRepository
import retrofit2.HttpException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var underTest: MainViewModel

    @Mock
    lateinit var repository: MainRepository

    @Mock
    lateinit var httpException: HttpException

    @Before
    fun setUp() {
        underTest = MainViewModel(repository, coroutinesTestRule.testDispatcherProvider)
    }

    @Test
    fun `will return list of countries when in parallel`() {
        whenSuccessfulResponse()

        underTest.getVariousItems()

        assertEquals(successResponse, underTest.liveDataParallel.value)
    }

    @Test
    fun `will return error state when in parallel`() {
        whenErrorResponse()
        underTest.getVariousItems()

        assertEquals(errorResponse, underTest.liveDataParallel.value)
    }

    @Test
    fun `will return list of countries when in sequential`() {
        whenSuccessfulResponse()
        underTest.liveDataSequential.observeForever {
            assertEquals(successResponse, it)
        }
    }

    @Test
    fun `will return error when in sequential`() {
        whenErrorResponse()
        underTest.liveDataSequential.observeForever {
            assertEquals(errorResponse, it)
        }
    }

    private fun whenErrorResponse() {
        `when`(httpException.localizedMessage).thenReturn(errorMessage)
        `when`(runBlocking { repository.listCountries() }).thenThrow(httpException)
    }

    private fun whenSuccessfulResponse() {
        `when`(runBlocking { repository.listCountries() }).thenReturn(list)
        `when`(runBlocking { repository.getCountryByName(country1.name!!) }).thenReturn(
            listOf(
                country1
            )
        )
        `when`(runBlocking { repository.getCountryByName(country2.name!!) }).thenReturn(
            listOf(
                country2
            )
        )
        `when`(runBlocking { repository.getCountryByName(country3.name!!) }).thenReturn(
            listOf(
                country3
            )
        )
    }

    companion object {
        val country1 = Country(name = "countryName1")
        val country2 = Country(name = "countryName2")
        val country3 = Country(name = "countryName3")

        const val errorMessage = "Error Message"

        val list = listOf(country1, country2, country3)
        val successResponse = MainUiStatus.SuccessStatus(list)
        val errorResponse = MainUiStatus.ErrorStatus(errorMessage)
    }
}