package blekel.sample.chucknorris.domain

import blekel.sample.chucknorris.data.repository.JokeRepository
import dagger.Reusable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

@Reusable
class JokeInteractor @Inject constructor(
    private val repository: JokeRepository
) {

    fun getNextJokes(): Single<List<Joke>> {
        return repository.loadJokes()
            .subscribeOn(Schedulers.io())
    }
}