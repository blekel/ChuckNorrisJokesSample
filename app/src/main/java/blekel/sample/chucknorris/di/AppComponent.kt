package blekel.sample.chucknorris.di

import blekel.sample.chucknorris.presentation.TheApp
import blekel.sample.chucknorris.presentation.jokes.JokesFragment
import blekel.sample.chucknorris.presentation.main.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(it: TheApp)
    fun inject(it: MainActivity)
    fun inject(it: JokesFragment)
}
