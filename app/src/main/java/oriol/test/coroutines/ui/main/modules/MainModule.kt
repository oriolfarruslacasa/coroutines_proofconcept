package oriol.test.coroutines.ui.main.modules

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import oriol.test.coroutines.ui.main.api.RetrofitService
import oriol.test.coroutines.ui.main.repositories.MainRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MainModule {

    fun getLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun getHeadersInterceptor() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original: Request = chain.request()

            val request: Request = original.newBuilder()
                .header("x-rapidapi-host", "restcountries-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "c2d0cec82fmsh7b0238405c06f23p1d7a56jsnabdd9b2e79a4")
                .build()

            return chain.proceed(request)
        }

    }

    fun getOkHttpClient() = OkHttpClient.Builder().apply {
        addInterceptor(getHeadersInterceptor())
        addInterceptor(getLoggingInterceptor())
    }

    var client: OkHttpClient = getOkHttpClient().build()

    fun getRetrofit() = Retrofit.Builder()
        .baseUrl("https://restcountries-v1.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun getService() = getRetrofit().create(RetrofitService::class.java)

    fun getRepository() = MainRepository(getService())
}