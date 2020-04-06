package oriol.test.coroutines.ui.main.repositories

import oriol.test.coroutines.ui.main.api.RetrofitService

class MainRepository(private val client: RetrofitService) {

    suspend fun listCountries() = client.listCountries()

    suspend fun getCountryByName(name : String) = client.getCountryByName(name)
}