package oriol.test.coroutines.ui.main

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.junit.MockitoJUnit
import oriol.test.coroutines.ui.main.repositories.MainRepository

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private lateinit var underTest : MainViewModel

    @Before
    fun setUp() {
        underTest = MainViewModel()
    }
}