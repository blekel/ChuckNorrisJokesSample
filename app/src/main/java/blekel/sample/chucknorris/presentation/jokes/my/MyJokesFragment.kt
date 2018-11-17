package blekel.sample.chucknorris.presentation.jokes.my

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View
import blekel.sample.chucknorris.di.manager.ComponentManager
import blekel.sample.chucknorris.presentation.jokes.BaseJokesFragment
import blekel.sample.chucknorris.presentation.jokes.add.AddJokeDialog
import blekel.sample.chucknorris.util.view.visible
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
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

    private var fabView: FloatingActionButton? = null
    private var scrollSubscription: Disposable? = null

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun getPresenter() = presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ComponentManager.getInstance().appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeScrollEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scrollSubscription?.dispose()
    }

    override fun setupFabView(fabView: FloatingActionButton) {
        this.fabView = fabView
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

    private fun observeScrollEvents() {
        scrollSubscription = RxRecyclerView.scrollEvents(binding.rvItems)
            .observeOn(Schedulers.computation())
            .buffer(300, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .map { events -> events.sumBy { it.dy() } }
            .filter { it != 0 }
            .map { it < 0 }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::showFab, Timber::e)
    }

    private fun showFab(state: Boolean) {
        if (state) {
            fabView?.show()
        } else {
            fabView?.hide()
        }
    }
}
