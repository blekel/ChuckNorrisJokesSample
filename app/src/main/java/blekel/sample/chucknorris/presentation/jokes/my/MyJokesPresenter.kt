package blekel.sample.chucknorris.presentation.jokes.my

import blekel.sample.chucknorris.domain.Joke
import blekel.sample.chucknorris.domain.JokeInteractor
import blekel.sample.chucknorris.domain.SettingsInteractor
import blekel.sample.chucknorris.presentation.jokes.BaseJokesPresenter
import blekel.sample.chucknorris.presentation.jokes.add.AddJokeMediator
import blekel.sample.chucknorris.presentation.jokes.model.JokeViewModel
import blekel.sample.chucknorris.util.common.IdUtils
import blekel.sample.chucknorris.util.rx.CompletableSubscriberSimple
import blekel.sample.chucknorris.util.rx.SubscriberSimple
import com.arellomobile.mvp.InjectViewState
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

@InjectViewState
class MyJokesPresenter @Inject constructor(
    private val interactor: JokeInteractor,
    private val addJokeMediator: AddJokeMediator,
    settingsInteractor: SettingsInteractor
) : BaseJokesPresenter(settingsInteractor) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        observeAddJoke()
    }

    override fun loadJokes(reload: Boolean) {
        addSubscription(
            interactor.getMyJokes()
                .observeOn(Schedulers.computation())
                .map {
                    mapToModels(it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    viewState.showLoading(true)
                }
                .doFinally {
                    viewState.showLoading(false)
                }
                .subscribe(SubscriberSimple.create { items ->
                    showingItems.clear()
                    showingItems.addAll(items)

                    viewState.showItems(showingItems)
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

    override fun mapToModel(item: Joke): JokeViewModel {
        val model = super.mapToModel(item)
        model.isShareVisible.set(false)
        model.isLikeVisible.set(false)
        model.isDeleteVisible.set(true)
        return model
    }

    private fun observeAddJoke() {
        addSubscription(
            addJokeMediator.observeAddJoke()
                .subscribeOn(Schedulers.computation())
                .map { text ->
                    Joke(IdUtils.createNewId(), text, false, true)
                }
                .flatMap {
                    interactor.save(it)
                        .andThen(Observable.just(it))
                }
                .map {
                    mapToModel(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { item ->
                    showingItems.add(item)
                    viewState.addItem(item)
                    viewState.scrollToEnd()
                }
        )
    }
}
