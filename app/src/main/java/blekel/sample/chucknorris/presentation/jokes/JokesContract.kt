package blekel.sample.chucknorris.presentation.jokes

import blekel.sample.chucknorris.presentation.jokes.model.JokeViewModel
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

interface JokesContract {

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View : MvpView {
        fun showItems(items: List<JokeViewModel>)
        fun addItem(item: JokeViewModel)
        fun removeItem(item: JokeViewModel)

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun shareItem(item: JokeViewModel)

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun scrollToEnd()
    }

    interface Presenter {
        fun loadJokes(reload: Boolean = false)
    }

    interface ItemClickListener {
        fun onShareClick(model: JokeViewModel)
        fun onLikeClick(model: JokeViewModel)
        fun onDeleteClick(model: JokeViewModel)
    }
}
