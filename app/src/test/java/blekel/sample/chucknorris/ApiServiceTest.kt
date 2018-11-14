package blekel.sample.chucknorris

import blekel.sample.chucknorris.data.api.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

class ApiServiceTest {

    lateinit var apiService: ApiService
    lateinit var gson: Gson

    @Before
    fun setUp() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.icndb.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        apiService = retrofit.create(ApiService::class.java)

        gson = GsonBuilder()
            .setPrettyPrinting()
            .create()
    }

    @Test
    fun test_fetchRandom() {
        val count = 5
        val result = apiService.getRandom(count)
            .blockingGet()
        println(gson.toJson(result))

        Assert.assertEquals("success", result.type)
        Assert.assertEquals(count, result.jokes.size)
    }
}