package oriol.test.coroutines.ui.main.api

import oriol.test.coroutines.ui.main.model.Country
import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitService {

    @GET("all")
    suspend fun listCountries(): List<Country>

    @GET("name/{name}")
    suspend fun getCountryByName(@Path("name") name: String): List<Country>
}