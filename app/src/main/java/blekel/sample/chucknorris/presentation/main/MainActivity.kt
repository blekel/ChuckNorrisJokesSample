package blekel.sample.chucknorris.presentation.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import blekel.sample.chucknorris.R
import blekel.sample.chucknorris.di.manager.ComponentManager
import blekel.sample.chucknorris.presentation.jokes.BaseJokesFragment
import blekel.sample.chucknorris.presentation.jokes.main.JokesFragment
import blekel.sample.chucknorris.presentation.jokes.model.JokeListType
import blekel.sample.chucknorris.presentation.jokes.my.MyJokesFragment
import blekel.sample.chucknorris.presentation.settings.SettingsFragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    MainContract.View {

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        setupNavigationDrawer()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_main -> {
                presenter.onMainJokesClick()
            }
            R.id.nav_my_jokes -> {
                presenter.onMyJokesClick()
            }
            R.id.nav_settings -> {
                presenter.onSettingsClick()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun openJokes(type: JokeListType) {
        setTitle(getTitle(type))

        supportFragmentManager.beginTransaction()
            .replace(R.id.vgContentRoot, getJokesFragment(type))
            .commitAllowingStateLoss()
    }

    override fun openSettings() {
        setTitle(R.string.nav_settings)

        supportFragmentManager.beginTransaction()
            .replace(R.id.vgContentRoot, SettingsFragment.newInstance())
            .commitAllowingStateLoss()
    }

    private fun getTitle(type: JokeListType): Int {
        return when (type) {
            JokeListType.MAIN -> R.string.nav_main
            JokeListType.MY_JOKES -> R.string.nav_my_jokes
        }
    }

    private fun getJokesFragment(type: JokeListType): BaseJokesFragment {
        return when (type) {
            JokeListType.MAIN -> JokesFragment.newInstance()
            JokeListType.MY_JOKES -> MyJokesFragment.newInstance()
        }
    }

    private fun setupNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun inject() {
        ComponentManager.getInstance().appComponent.inject(this)
    }
}
