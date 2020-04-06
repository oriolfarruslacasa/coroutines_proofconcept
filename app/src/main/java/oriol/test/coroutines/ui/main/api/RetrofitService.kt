package oriol.test.coroutines.ui.main.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitService {

    @GET("all")
    suspend fun listRepos(): String
}