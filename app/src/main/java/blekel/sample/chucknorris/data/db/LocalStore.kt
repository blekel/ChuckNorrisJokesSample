package blekel.sample.chucknorris.data.db

import blekel.sample.chucknorris.domain.Joke
import blekel.sample.chucknorris.util.realm.RealmUtils
import dagger.Reusable
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

@Reusable
class JokeLocalStore @Inject constructor() : BaseLocalStore<JokeDb>(JokeDb::class.java) {

    fun save(item: Joke) {
        RealmUtils.transaction { realm ->
            val itemDb = findOrCreate(item.id)
            with(itemDb) {
                text = item.text
                isLiked = item.isLiked
                isMy = item.isMy
            }
            realm.insertOrUpdate(itemDb)
        }
    }

    fun save(items: List<Joke>) {
        RealmUtils.transaction { realm ->
            items.forEach {
                save(it)
            }
        }
    }

    fun delete(item: Joke) {
        RealmUtils.transaction { realm ->
            delete(item.id)
        }
    }

    fun getByIds(ids: List<String>): List<Joke> {
        return RealmUtils.transaction { realm ->
            findByIds(ids)
                .map { mapItem(it) }
        }
    }

    fun getAll(): List<Joke> {
        return RealmUtils.transaction { realm ->
            findAll()
                .map { mapItem(it) }
        }
    }

    fun getMyJokes(): List<Joke> {
        return RealmUtils.transaction { realm ->
            query()
                .beginGroup()
                .equalTo(JokeDb.FIELD_LIKED, true)
                .or()
                .equalTo(JokeDb.FIELD_MY, true)
                .findAll()
                .map {
                    mapItem(it)
                }
        }
    }

    private fun mapItem(it: JokeDb) = Joke(it.id, it.text, it.isLiked, it.isMy)
}
