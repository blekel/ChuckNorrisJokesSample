package blekel.sample.chucknorris.presentation.jokes.main

import android.content.Context
import android.hardware.SensorManager
import blekel.sample.chucknorris.di.manager.ComponentManager
import blekel.sample.chucknorris.presentation.jokes.BaseJokesFragment
import blekel.sample.chucknorris.presentation.jokes.reloadJokes
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.squareup.seismic.ShakeDetector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

class JokesFragment : BaseJokesFragment(), ShakeDetector.Listener {

    companion object {
        fun newInstance() = JokesFragment()
    }

    @Inject
    @InjectPresenter
    internal lateinit var presenter: JokesPresenter

    @Inject
    internal lateinit var sensorManager: SensorManager

    private lateinit var shakeDetector: ShakeDetector

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun getPresenter() = presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ComponentManager.getInstance().appComponent.inject(this)
        shakeDetector = ShakeDetector(this)
    }

    override fun onResume() {
        super.onResume()
        shakeDetector.start(sensorManager)
    }

    override fun onPause() {
        super.onPause()
        shakeDetector.stop()
    }

    override fun hearShake() {
        Timber.d("shake gesture detected")
        presenter.reloadJokes()
    }
}
