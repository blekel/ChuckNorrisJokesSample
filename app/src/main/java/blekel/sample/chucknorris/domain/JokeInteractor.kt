package blekel.sample.chucknorris.domain

import blekel.sample.chucknorris.data.repository.JokeRepository
import dagger.Reusable
import io.reactivex.Completable
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

    fun save(item: Joke): Completable {
        return Completable.fromAction {
            repository.save(item)
        }.subscribeOn(Schedulers.io())
    }

    fun delete(item: Joke): Completable {
        return Completable.fromAction {
            repository.delete(item)
        }.subscribeOn(Schedulers.io())
    }

    fun loadNextJokes(): Single<List<Joke>> {
        return repository.loadJokes()
            .subscribeOn(Schedulers.io())
            .flatMap { items ->
                restoreItemsFromLocal(items)
            }
            .doOnSuccess { items ->
                repository.save(items)
            }
    }

    fun getMyJokes(): Single<List<Joke>> {
        return repository.getMyCachedJokes()
    }

    private fun restoreItemsFromLocal(items: List<Joke>): Single<List<Joke>> {
        val ids = items.map { it.id }
        return repository.getCachedJokes(ids)
            .map { cachedItems ->
                restoreItems(items, cachedItems)
            }
    }

    private fun restoreItems(items: List<Joke>, cachedItems: List<Joke>): List<Joke> {
        val likedIds = cachedItems
            .filter { it.isLiked }
            .map { it.id }
            .toHashSet()

        items.forEach { item ->
            item.isLiked = likedIds.contains(item.id)
        }

        return items
    }
}