package blekel.sample.chucknorris.data.repository

import blekel.sample.chucknorris.data.api.JokeRemoteStore
import blekel.sample.chucknorris.data.db.JokeLocalStore
import blekel.sample.chucknorris.domain.Joke
import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

@Reusable
class JokeRepository @Inject constructor(
    private val remoteStore: JokeRemoteStore,
    private val localStore: JokeLocalStore
) {

    fun save(item: Joke) {
        localStore.save(item)
    }

    fun save(items: List<Joke>) {
        localStore.save(items)
    }

    fun delete(item: Joke) {
        localStore.delete(item)
    }

    fun loadJokes(): Single<List<Joke>> {
        return remoteStore.loadJokes()
    }

    fun getCachedJokes(): Single<List<Joke>> {
        return Single.fromCallable {
            localStore.getAll()
        }
    }

    fun getCachedJokes(ids: List<String>): Single<List<Joke>> {
        return Single.fromCallable {
            localStore.getByIds(ids)
        }
    }

    fun getMyCachedJokes(): Single<List<Joke>> {
        return Single.fromCallable {
            localStore.getAll()
                .filter { it.isLiked || it.isMy }
        }
    }
}