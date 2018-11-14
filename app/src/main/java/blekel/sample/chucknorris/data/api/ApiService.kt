package blekel.sample.chucknorris.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

interface ApiService {

    @GET("jokes/random/{count}")
    fun getRandom(@Path("count") count: Int): Single<ListResponseDto>
}
