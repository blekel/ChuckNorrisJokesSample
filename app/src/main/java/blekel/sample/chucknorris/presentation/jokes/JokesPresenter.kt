package blekel.sample.chucknorris.presentation.jokes

import blekel.sample.chucknorris.domain.Joke
import blekel.sample.chucknorris.domain.JokeInteractor
import blekel.sample.chucknorris.presentation.base.BasePresenter
import blekel.sample.chucknorris.presentation.jokes.model.JokeListType
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
    private val interactor: JokeInteractor
) : BasePresenter<JokesContract.View>(),
    JokesContract.Presenter,
    JokesContract.ItemClickListener {

    private lateinit var type: JokeListType
    private val showingItems = mutableListOf<JokeViewModel>()

    override fun init(type: JokeListType) {
        this.type = type
    }

    override fun loadJokes() {
        addSubscription(
            interactor.loadNextJokes()
                .observeOn(Schedulers.computation())
                .map { mapItems(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(SubscriberSimple.create { items ->
                    showingItems.addAll(items)
                    viewState.showJokes(items)
                })
        )
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

    override fun onDeleteClick(model: JokeViewModel) {
        // TODO: impl
    }

    private fun mapItems(items: List<Joke>): List<JokeViewModel> {
        return items.map {
            val model = JokeViewModel(it, this)
            model.isShareVisible.set(type == JokeListType.MAIN)
            model.isLikeVisible.set(type == JokeListType.MAIN && !it.isLiked)
            model.isDeleteVisible.set(type == JokeListType.MY_JOKES)
            model
        }
    }
}
