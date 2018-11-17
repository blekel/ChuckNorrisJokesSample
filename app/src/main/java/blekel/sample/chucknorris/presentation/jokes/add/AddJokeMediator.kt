package blekel.sample.chucknorris.presentation.jokes.add

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

@Singleton
class AddJokeMediator @Inject constructor() {

    private val publisher = PublishSubject.create<String>()

    fun addJoke(text: String) {
        publisher.onNext(text)
    }

    fun observeAddJoke(): Observable<String> {
        return publisher
    }
}