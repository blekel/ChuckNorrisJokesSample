package blekel.sample.chucknorris.presentation.jokes

import blekel.sample.chucknorris.domain.Joke
import blekel.sample.chucknorris.domain.JokeInteractor
import blekel.sample.chucknorris.presentation.base.BasePresenter
import blekel.sample.chucknorris.presentation.jokes.model.JokeListType
import blekel.sample.chucknorris.presentation.jokes.model.JokeViewModel
import blekel.sample.chucknorris.util.rx.SubscriberSimple
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

@InjectViewState
class JokesPresenter @Inject constructor(
    private val interactor: JokeInteractor
) : BasePresenter<JokesContract.View>(), JokesContract.Presenter {

    private lateinit var type: JokeListType
    private val showingItems = mutableListOf<JokeViewModel>()

    fun init(type: JokeListType) {
        this.type = type
    }

    fun loadJokes() {
        addSubscription(
            interactor.getNextJokes()
                .observeOn(Schedulers.computation())
                .map { mapItems(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SubscriberSimple.create { items ->
                    showingItems.addAll(items)
                    viewState.showJokes(items)
                })
        )
    }

    private fun mapItems(items: List<Joke>): List<JokeViewModel> {
        return items.map {
            val model = JokeViewModel(it.id, it.text)
            model.isShareVisible.set(type == JokeListType.MAIN)
            model.isLikeVisible.set(type == JokeListType.MAIN)
            model.isDeleteVisible.set(type == JokeListType.MY_JOKES)
            model
        }
    }
}