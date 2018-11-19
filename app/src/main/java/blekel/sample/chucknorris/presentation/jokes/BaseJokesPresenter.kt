package blekel.sample.chucknorris.presentation.jokes

import android.os.Build
import android.text.Html
import android.text.Spanned
import blekel.sample.chucknorris.domain.Features
import blekel.sample.chucknorris.domain.Joke
import blekel.sample.chucknorris.domain.SettingsInteractor
import blekel.sample.chucknorris.presentation.base.BasePresenter
import blekel.sample.chucknorris.presentation.jokes.model.JokeViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

abstract class BaseJokesPresenter(private val settingsInteractor: SettingsInteractor) :
    BasePresenter<JokesContract.View>(),
    JokesContract.Presenter,
    JokesContract.ItemClickListener {

    protected val showingItems = mutableListOf<JokeViewModel>()
    protected val showLoadingManager = ShowLoadingManager()

    override fun onShareClick(model: JokeViewModel) {
        // nop
    }

    override fun onLikeClick(model: JokeViewModel) {
        // nop
    }

    override fun onDeleteClick(model: JokeViewModel) {
        // nop
    }

    protected fun showLoading(state: Boolean) {
        showLoadingManager.showLoading(state)
    }

    protected open fun mapToModel(item: Joke): JokeViewModel {
        return prepareModel(JokeViewModel(item, this))
    }

    protected fun mapToModels(items: List<Joke>): List<JokeViewModel> {
        return items.map {
            mapToModel(it)
        }
    }

    private fun prepareModel(model: JokeViewModel): JokeViewModel {
        val text = model.item.text
        val preparedText = textToHtml(replaceName(text))

        model.text.set(preparedText)
        return model
    }

    private fun replaceName(text: String): String {
        if (settingsInteractor.isUserOverride()) {
            val userName = settingsInteractor.getUserName()
            val userFirstname = settingsInteractor.getUserFirstname()
            val userLastname = settingsInteractor.getUserLastname()

            return text
                .replace(JokesContract.DEFAULT_FULL_NAME, userName)
                .replace(JokesContract.DEFAULT_FIRSTNAME, userFirstname)
                .replace(JokesContract.DEFAULT_LASTNAME, userLastname)
        }
        return text
    }

    private fun textToHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(source)
        }
    }

    /**
     * Helper for better loading animation UX.
     */
    protected inner class ShowLoadingManager {
        var isLoading = false
        private var disposable: Disposable? = null

        fun showLoading(state: Boolean) {
            @Suppress("ConstantConditionIf")
            if (Features.DELAY_SHOW_LOADING) {
                showLoadingDelayed(state)
            } else {
                showLoadingImpl(state)
            }
        }

        private fun showLoadingImpl(state: Boolean) {
            viewState.showLoading(state)
            isLoading = state
        }

        private fun showLoadingDelayed(state: Boolean) {
            val delay = if (state) 0L else 750L

            disposable?.dispose()
            disposable = Observable.timer(delay, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showLoadingImpl(state)
                }, Timber::e)
            addSubscription(disposable)
        }
    }
}
