package blekel.sample.chucknorris.presentation.jokes

import blekel.sample.chucknorris.presentation.jokes.model.JokeListType
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
        fun showJokes(items: List<JokeViewModel>)

        @StateStrategyType(OneExecutionStateStrategy::class)
        fun shareItem(item: JokeViewModel)
    }

    interface Presenter {
        fun init(type: JokeListType)
        fun loadJokes()
    }

    interface ItemClickListener {
        fun onShareClick(model: JokeViewModel)
        fun onLikeClick(model: JokeViewModel)
        fun onDeleteClick(model: JokeViewModel)
    }
}
