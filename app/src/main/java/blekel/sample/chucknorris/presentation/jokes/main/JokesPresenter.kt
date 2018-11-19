package blekel.sample.chucknorris.presentation.jokes.main

import blekel.sample.chucknorris.domain.Joke
import blekel.sample.chucknorris.domain.JokeInteractor
import blekel.sample.chucknorris.domain.SettingsInteractor
import blekel.sample.chucknorris.presentation.jokes.BaseJokesPresenter
import blekel.sample.chucknorris.presentation.jokes.model.JokeViewModel
import blekel.sample.chucknorris.util.rx.CompletableSubscriberSimple
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
    private val interactor: JokeInteractor,
    settingsInteractor: SettingsInteractor
) : BaseJokesPresenter(settingsInteractor) {

    override fun loadJokes(reload: Boolean) {
        addSubscription(
            interactor.loadNextJokes()
                .observeOn(Schedulers.computation())
                .map {
                    mapToModels(it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    showLoading(true)
                }
                .doFinally {
                    showLoading(false)
                }
                .subscribe(SubscriberSimple.create { items ->
                    if (reload) {
                        showingItems.clear()
                    }
                    showingItems.addAll(items)
                    viewState.showItems(showingItems)
                })
        )
    }

    override fun onLastItemReached() {
        if (showLoadingManager.isLoading) {
            return
        }
        loadJokes()
    }

    override fun onShareClick(model: JokeViewModel) {
        viewState.shareItem(model)
    }

    override fun onLikeClick(model: JokeViewModel) {
        model.item.isLiked = true

        interactor.save(model.item)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(CompletableSubscriberSimple.create {
                model.isLikeVisible.set(false)
            })
    }

    override fun mapToModel(item: Joke): JokeViewModel {
        val model = super.mapToModel(item)
        model.isShareVisible.set(true)
        model.isLikeVisible.set(!item.isLiked)
        model.isDeleteVisible.set(false)
        return model
    }
}
