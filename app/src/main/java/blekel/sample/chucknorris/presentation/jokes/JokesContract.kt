package blekel.sample.chucknorris.presentation.jokes

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

interface JokesContract {

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View : MvpView {
    }

    interface Presenter {
    }
}
