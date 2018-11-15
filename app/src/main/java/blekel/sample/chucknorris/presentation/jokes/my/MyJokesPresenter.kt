package blekel.sample.chucknorris.presentation.jokes.my

import blekel.sample.chucknorris.domain.JokeInteractor
import blekel.sample.chucknorris.presentation.jokes.BaseJokesPresenter
import blekel.sample.chucknorris.presentation.jokes.model.JokeListType
import blekel.sample.chucknorris.presentation.jokes.model.JokeViewModel
import blekel.sample.chucknorris.util.rx.CompletableSubscriberSimple
import blekel.sample.chucknorris.util.rx.SubscriberSimple
import com.arellomobile.mvp.InjectViewState
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

@InjectViewState
class MyJokesPresenter @Inject constructor(
    private val interactor: JokeInteractor
) : BaseJokesPresenter() {

    override fun loadJokes(reload: Boolean) {
        addSubscription(
            interactor.getMyJokes()
                .observeOn(Schedulers.computation())
                .map {
                    mapItems(it, JokeListType.MY_JOKES)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SubscriberSimple.create { items ->
                    showingItems.clear()
                    showingItems.addAll(items)

                    viewState.showJokes(showingItems)
                })
        )
    }

    override fun onDeleteClick(model: JokeViewModel) {
        val item = model.item
        var operation: Completable? = null

        when {
            item.isLiked -> {
                item.isLiked = false
                operation = interactor.save(model.item)
            }
            item.isMy -> {
                operation = interactor.delete(model.item)
            }
        }
        if (operation != null) {
            operation
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(CompletableSubscriberSimple.create {
                    showingItems.remove(model)
                    viewState.removeItem(model)
                })
        }
    }
}
