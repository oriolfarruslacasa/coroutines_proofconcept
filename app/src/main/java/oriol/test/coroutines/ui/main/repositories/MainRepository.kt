package oriol.test.coroutines.ui.main.repositories

import oriol.test.coroutines.ui.main.api.RetrofitService

class MainRepository(private val client: RetrofitService) {

    suspend fun getData() = client.listRepos()
}