package blekel.sample.chucknorris.presentation.main

import blekel.sample.chucknorris.presentation.jokes.model.JokeListType
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

interface MainContract {

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View : MvpView {
        fun openJokes(type: JokeListType)
        fun openSettings()
    }

    interface Presenter {
        fun onMainJokesClick()
        fun onMyJokesClick()
        fun onSettingsClick()
    }
}
