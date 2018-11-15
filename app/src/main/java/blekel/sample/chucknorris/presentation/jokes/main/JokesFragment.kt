package blekel.sample.chucknorris.presentation.jokes.main

import android.content.Context
import blekel.sample.chucknorris.databinding.FragmentJokesBinding
import blekel.sample.chucknorris.di.manager.ComponentManager
import blekel.sample.chucknorris.presentation.jokes.BaseJokesFragment
import blekel.sample.chucknorris.presentation.jokes.JokesAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

class JokesFragment : BaseJokesFragment() {

    companion object {
        fun newInstance() = JokesFragment()
    }

    @Inject
    @InjectPresenter
    internal lateinit var presenter: JokesPresenter

    private lateinit var binding: FragmentJokesBinding
    private val adapter = JokesAdapter()

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun getPresenter() = presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ComponentManager.getInstance().appComponent.inject(this)
    }
}
