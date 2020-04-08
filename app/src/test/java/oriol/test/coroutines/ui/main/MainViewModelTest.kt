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

    @Before
    fun setUp() {
        underTest = MainViewModel(repository, coroutinesTestRule.testDispatcherProvider)

        `when`(runBlocking { repository.listCountries() }).thenReturn(list)
        `when`(runBlocking { repository.getCountryByName(country1.name!!) }).thenReturn(listOf(country1))
        `when`(runBlocking { repository.getCountryByName(country2.name!!) }).thenReturn(listOf(country2))
        `when`(runBlocking { repository.getCountryByName(country3.name!!) }).thenReturn(listOf(country3))
    }

    @Test
    fun `will return list of countries in parallel`() {
        underTest.getVariousItems()

        assertEquals(list, underTest.liveDataParallel.value)
    }

    @Test
    fun `will return list of countries in sequential`() {
        underTest.liveDataSequential.observeForever {
            assertEquals(list, it)
        }
    }

    companion object {
        val country1 = Country(name = "countryName1")
        val country2 = Country(name = "countryName2")
        val country3 = Country(name = "countryName3")

        val list = listOf(country1, country2, country3)
    }
}