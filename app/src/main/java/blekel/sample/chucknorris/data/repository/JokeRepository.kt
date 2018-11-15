package blekel.sample.chucknorris.data.repository

import blekel.sample.chucknorris.data.api.JokeRemoteStore
import blekel.sample.chucknorris.domain.Joke
import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

@Reusable
class JokeRepository @Inject constructor(
    private val remoteStore: JokeRemoteStore
) {

    fun loadJokes(): Single<List<Joke>> {
        return remoteStore.loadJokes()
    }
}