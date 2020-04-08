package oriol.test.coroutines.ui.main

import androidx.lifecycle.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import oriol.test.coroutines.ui.main.model.Country
import oriol.test.coroutines.ui.main.repositories.MainRepository

class MainViewModel(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _liveDataParallel: MutableLiveData<List<Country>> = MutableLiveData()
    val liveDataParallel: LiveData<List<Country>> = _liveDataParallel

    val liveDataSequential: LiveData<List<Country>> = liveData(dispatchers.io()) {
        val retrievedData = repository.listCountries()
        val listOfLists = listOf( //These calls are made sequentially
            repository.getCountryByName(retrievedData.first().name ?: ""),
            repository.getCountryByName(retrievedData[1].name ?: ""),
            repository.getCountryByName(retrievedData[2].name ?: "")
        )
        val finalList = listOfLists[0] + listOfLists[1] + listOfLists[2]
        emit(finalList)
    }

    fun getVariousItems() {
        viewModelScope.launch(dispatchers.main()) {
            val retrievedData = repository.listCountries()
            //These calls are made in parallel
            val deferredOne =
                async { repository.getCountryByName(retrievedData.first().name ?: "") }
            val deferredTwo = async { repository.getCountryByName(retrievedData[1].name ?: "") }
            val deferredThree = async { repository.getCountryByName(retrievedData[2].name ?: "") }

            val result = deferredOne.await() + deferredTwo.await() + deferredThree.await()

            _liveDataParallel.value = result
        }
    }
}
