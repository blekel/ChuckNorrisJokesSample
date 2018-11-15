package blekel.sample.chucknorris.presentation.jokes.my

import android.content.Context
import android.support.design.widget.FloatingActionButton
import blekel.sample.chucknorris.di.manager.ComponentManager
import blekel.sample.chucknorris.presentation.jokes.BaseJokesFragment
import blekel.sample.chucknorris.presentation.jokes.add.AddJokeDialog
import blekel.sample.chucknorris.util.view.visible
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

class MyJokesFragment : BaseJokesFragment() {

    companion object {
        fun newInstance() = MyJokesFragment()
    }

    @Inject
    @InjectPresenter
    internal lateinit var presenter: MyJokesPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun getPresenter() = presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ComponentManager.getInstance().appComponent.inject(this)
    }

    override fun setupFabView(fabView: FloatingActionButton) {
        fabView.setOnClickListener {
            showAddJokeDialog()
        }
        fabView.visible = true
    }

    private fun showAddJokeDialog() {
        AddJokeDialog
            .newInstance()
            .show(fragmentManager, AddJokeDialog.TAG)
    }
}
