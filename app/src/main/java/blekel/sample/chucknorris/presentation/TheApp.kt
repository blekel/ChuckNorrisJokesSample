package blekel.sample.chucknorris.presentation

import android.app.Application
import blekel.sample.chucknorris.di.manager.ComponentManager
import io.realm.Realm
import timber.log.Timber

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

class TheApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initLogger()
        initRealm()
        initDagger()
    }

    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initRealm() {
        Realm.init(this)
    }

    private fun initDagger() {
        ComponentManager.init(this)
        ComponentManager.getInstance().appComponent.inject(this)
    }
}