package blekel.sample.chucknorris.presentation.jokes.add

import blekel.sample.chucknorris.presentation.jokes.add.model.AddJokeViewModel
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

interface AddJokeContract {

    companion object {
        const val INPUT_LIMIT = 120
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View : MvpView {
        fun setModel(model: AddJokeViewModel)
    }

    interface Presenter {
        fun onInputChanged()
        fun onSaveClick()
        fun onCancelClick()
    }
}
