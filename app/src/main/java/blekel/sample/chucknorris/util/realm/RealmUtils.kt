package blekel.sample.chucknorris.util.realm

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.realm.Realm

/**
 * Created by Vitaliy Levonyuk on 20.06.17
 */

object RealmUtils {

    fun <T> singleTransaction(func1: Function1<Realm, T>): Single<T> {
        return Single.fromCallable { transaction(func1) }
    }

    fun completableTransaction(action: Consumer<Realm>): Completable {
        return Completable.fromAction { transactionAction(action) }
    }

    fun transactionAction(action: Consumer<Realm>) {
        transaction<Any?> { realm ->
            action.accept(realm)
            null
        }
    }

    fun <R> transaction(action: Function1<Realm, R>): R {
        val realm = Realm.getDefaultInstance()

        val outerTransaction = realm.isInTransaction
        if (!outerTransaction) {
            realm.beginTransaction()
        }
        try {
            val ret = action.invoke(realm)

            if (!outerTransaction) {
                realm.commitTransaction()
            }
            return ret

        } catch (e: Exception) {
            if (!outerTransaction) {
                realm.cancelTransaction()
            }
            throw e

        } finally {
            realm.close()
        }
    }
}
