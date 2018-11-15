package blekel.sample.chucknorris.presentation.jokes

import blekel.sample.chucknorris.presentation.base.BasePresenter
import com.arellomobile.mvp.InjectViewState
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

@InjectViewState
class JokesPresenter @Inject constructor() : BasePresenter<JokesContract.View>(),
    JokesContract.Presenter {

}
