package blekel.sample.chucknorris.presentation.jokes

import blekel.sample.chucknorris.domain.Joke
import blekel.sample.chucknorris.presentation.base.BasePresenter
import blekel.sample.chucknorris.presentation.jokes.model.JokeViewModel

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

abstract class BaseJokesPresenter : BasePresenter<JokesContract.View>(),
    JokesContract.Presenter,
    JokesContract.ItemClickListener {

    protected val showingItems = mutableListOf<JokeViewModel>()

    override fun onShareClick(model: JokeViewModel) {
        // nop
    }

    override fun onLikeClick(model: JokeViewModel) {
        // nop
    }

    override fun onDeleteClick(model: JokeViewModel) {
        // nop
    }

    protected open fun mapToModel(item: Joke): JokeViewModel {
        return JokeViewModel(item, this)
    }

    protected fun mapToModels(items: List<Joke>): List<JokeViewModel> {
        return items.map {
            mapToModel(it)
        }
    }
}
