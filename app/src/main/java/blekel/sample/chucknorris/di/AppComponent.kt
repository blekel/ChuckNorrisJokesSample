package blekel.sample.chucknorris.di

import blekel.sample.chucknorris.presentation.TheApp
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {
    fun inject(it: TheApp)
}
