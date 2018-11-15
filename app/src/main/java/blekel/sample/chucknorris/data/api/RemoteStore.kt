package blekel.sample.chucknorris.data.api

import blekel.sample.chucknorris.domain.Joke
import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

@Reusable
class JokeRemoteStore @Inject constructor(
    private val apiService: ApiService
) {
    companion object {
        private const val PAGE_SIZE = 20
    }

    fun loadJokes(): Single<List<Joke>> {
        return apiService.getRandom(PAGE_SIZE)
            .map { it.jokes }
            .flattenAsObservable { it -> it }
            .map { dto ->
                Joke(dto.id.toString(), dto.text)
            }
            .toList()
    }
}
