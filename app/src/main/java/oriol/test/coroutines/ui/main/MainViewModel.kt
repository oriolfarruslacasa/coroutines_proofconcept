package oriol.test.coroutines.ui.main

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import oriol.test.coroutines.ui.main.model.Country
import oriol.test.coroutines.ui.main.modules.MainModule
import oriol.test.coroutines.ui.main.repositories.MainRepository

class MainViewModel : ViewModel() {

    private val repository: MainRepository by lazy {
        MainModule.getRepository()
    }

    val data: LiveData<List<Country>> = liveData(Dispatchers.IO) {
        val retrievedData = repository.listCountries()
        val listOfLists = listOf(
            repository.getCountryByName(retrievedData.first().name ?: ""),
            repository.getCountryByName(retrievedData[1].name ?: ""),
            repository.getCountryByName(retrievedData[2].name ?: ""),
            repository.getCountryByName(retrievedData[3].name ?: ""),
            repository.getCountryByName(retrievedData[4].name ?: "")
        )
        val finalList = listOfLists[0] + listOfLists[1]
        emit(finalList)
    }

    val data2: MutableLiveData<List<Country>> = MutableLiveData()

    fun getVariousItems() {
        viewModelScope.launch(Dispatchers.Main) {
            val retrievedData = repository.listCountries()

            val deferredOne = async { repository.getCountryByName(retrievedData.first().name ?: "") }
            val deferredTwo = async { repository.getCountryByName(retrievedData[1].name ?: "") }
            val deferredThree = async { repository.getCountryByName(retrievedData[2].name ?: "") }

            val result = deferredOne.await() + deferredTwo.await() + deferredThree.await()

            data2.value = result
        }
    }
}
