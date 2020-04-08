package oriol.test.coroutines.ui.main.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import oriol.test.coroutines.ui.main.api.RetrofitService
import oriol.test.coroutines.ui.main.model.Country


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainRepositoryTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    private lateinit var underTest : MainRepository

    @Mock
    lateinit var retrofitService : RetrofitService

    @Before
    fun setUp() {
        underTest = MainRepository(retrofitService)
    }

    @Test
    fun `will return value when ask for list of countries`() = runBlockingTest{
        `when`(retrofitService.listCountries()).thenReturn(listOf(country))

        val result = underTest.listCountries()

        assertEquals(listOf(country), result)
    }

    @Test
    fun `will return value when ask for a country`() = runBlockingTest{
        `when`(retrofitService.getCountryByName(countryName)).thenReturn(listOf(country))

        val result = underTest.getCountryByName(countryName)

        assertEquals(listOf(country), result)
    }

    companion object{
        val countryName = "SomeCountryName"
        val country = Country(name = countryName)
    }
}