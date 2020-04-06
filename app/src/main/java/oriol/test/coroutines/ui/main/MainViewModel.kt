package oriol.test.coroutines.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import oriol.test.coroutines.ui.main.model.Country
import oriol.test.coroutines.ui.main.modules.MainModule
import oriol.test.coroutines.ui.main.repositories.MainRepository

class MainViewModel : ViewModel() {

    private val repository: MainRepository by lazy {
        MainModule.getRepository()
    }

    val data: LiveData<List<Country>> = liveData(Dispatchers.IO) {
        val retrievedData = repository.listCountries()
        val oneCountry = repository.getCountryByName(retrievedData.first().name ?: "")
        emit(oneCountry)
    }
}
