package blekel.sample.chucknorris.presentation

import android.app.Application
import blekel.sample.chucknorris.di.manager.ComponentManager
import timber.log.Timber

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

class TheApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initLogger()
        initDagger()
    }

    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initDagger() {
        ComponentManager.init(this)
        ComponentManager.getInstance().appComponent.inject(this)
    }
}