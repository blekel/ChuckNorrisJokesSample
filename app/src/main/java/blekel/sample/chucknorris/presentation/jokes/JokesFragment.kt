package blekel.sample.chucknorris.presentation.jokes

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import blekel.sample.chucknorris.R
import blekel.sample.chucknorris.databinding.FragmentJokesBinding
import blekel.sample.chucknorris.di.manager.ComponentManager
import blekel.sample.chucknorris.presentation.jokes.model.JokeListType
import blekel.sample.chucknorris.presentation.jokes.model.JokeViewModel
import blekel.sample.chucknorris.util.view.visible
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import javax.inject.Inject

class JokesFragment : MvpAppCompatFragment(), JokesContract.View {

    companion object {
        private const val PARAM_TYPE = "type"

        fun newInstance(type: JokeListType): JokesFragment {
            val ret = JokesFragment()
            val args = Bundle()
            args.putString(PARAM_TYPE, type.name)
            ret.arguments = args
            return ret
        }

        private fun getTypeParam(self: JokesFragment): JokeListType {
            val param = self.arguments!!.getString(PARAM_TYPE)!!
            return JokeListType.lookup(param)
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: JokesPresenter

    private lateinit var binding: FragmentJokesBinding
    private val adapter = JokesAdapter()

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ComponentManager.getInstance().appComponent.inject(this)

        val type = getTypeParam(this)
        presenter.init(type)
    }

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
        presenter.loadJokes()
    }

    override fun showJokes(items: List<JokeViewModel>) {
        adapter.setItems(items)
        updateEmptyView()
    }

    override fun removeItem(item: JokeViewModel) {
        adapter.removeItem(item)
        updateEmptyView()
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

    private fun updateEmptyView() {
        binding.tvEmpty.visible = adapter.itemCount == 0
    }
}
