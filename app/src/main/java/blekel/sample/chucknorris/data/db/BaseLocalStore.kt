package blekel.sample.chucknorris.data.db

import blekel.sample.chucknorris.util.realm.RealmUtils
import io.realm.RealmObject
import io.realm.RealmQuery

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

abstract class BaseLocalStore<T : RealmObject>
protected constructor(private val typeClass: Class<T>) {

    companion object {
        private const val ID = "id"
    }

    protected fun query(): RealmQuery<T> {
        return RealmUtils.transaction { realm -> realm.where(typeClass) }
    }

    protected fun findAll(): List<T> {
        return RealmUtils.transaction { realm -> realm.where(typeClass).findAll() }
    }

    protected fun findById(id: String): T? {
        return RealmUtils.transaction { realm ->
            realm.where(typeClass).equalTo(ID, id).findFirst()
        }
    }

    protected fun findByIds(ids: List<String>): List<T> {
        return RealmUtils.transaction { realm ->
            val idsParam = ids.toTypedArray()
            realm.where(typeClass)
                .`in`(ID, idsParam)
                .findAll()
        }
    }

    protected fun findOrCreate(id: String): T {
        return RealmUtils.transaction { realm ->
            val ret = findById(id)
            ret ?: realm.createObject(typeClass, id)
        }
    }

    protected fun delete(id: String): Boolean {
        return RealmUtils.transaction {
            val ret = findById(id)
            ret?.deleteFromRealm()
            ret != null
        }
    }
}
