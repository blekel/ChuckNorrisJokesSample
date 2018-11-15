package blekel.sample.chucknorris.presentation.main

import blekel.sample.chucknorris.presentation.base.BasePresenter
import blekel.sample.chucknorris.presentation.jokes.model.JokeListType
import com.arellomobile.mvp.InjectViewState
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

@InjectViewState
class MainPresenter @Inject constructor() : BasePresenter<MainContract.View>(),
    MainContract.Presenter {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.openJokes(JokeListType.MAIN)
    }

    override fun onMainJokesClick() {
        viewState.openJokes(JokeListType.MAIN)
    }

    override fun onMyJokesClick() {
        viewState.openJokes(JokeListType.MY_JOKES)
    }

    override fun onSettingsClick() {
        // TODO: impl
    }
}
