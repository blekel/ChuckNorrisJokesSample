package blekel.sample.chucknorris.presentation.jokes

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import blekel.sample.chucknorris.R
import blekel.sample.chucknorris.databinding.FragmentJokesBinding
import blekel.sample.chucknorris.presentation.jokes.model.JokeViewModel
import blekel.sample.chucknorris.presentation.main.MainActivity
import blekel.sample.chucknorris.util.view.visible
import com.arellomobile.mvp.MvpAppCompatFragment
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

abstract class BaseJokesFragment : MvpAppCompatFragment(), JokesContract.View {

    protected lateinit var binding: FragmentJokesBinding
    private val adapter = JokesAdapter()

    abstract fun getPresenter(): JokesContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jokes, container, false)

        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().loadJokes()
    }

    override fun showItems(items: List<JokeViewModel>) {
        adapter.setItems(items)
        updateEmptyView()
    }

    override fun addItem(item: JokeViewModel) {
        adapter.addItem(item)
        updateEmptyView()
    }

    override fun removeItem(item: JokeViewModel) {
        adapter.removeItem(item)
        updateEmptyView()
    }

    override fun scrollToEnd() {
        binding.root.postDelayed({
            val lastIndex = adapter.itemCount
            binding.rvItems.smoothScrollToPosition(lastIndex)
        }, 300)
    }

    override fun shareItem(item: JokeViewModel) {
        val context = context ?: return
        val text = item.item.text
        val title = "Share"

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TITLE, title)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.type = "text/plain"

        context.startActivity(Intent.createChooser(shareIntent, title))
    }

    override fun onResume() {
        super.onResume()
        val fabView = (activity as MainActivity).fab
        setupFabView(fabView)
    }

    open fun setupFabView(fabView: FloatingActionButton) {
        fabView.visible = false
    }

    private fun updateEmptyView() {
        binding.tvEmpty.visible = adapter.itemCount == 0
    }
}
