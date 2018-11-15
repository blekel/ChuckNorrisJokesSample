package blekel.sample.chucknorris.presentation.jokes

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import blekel.sample.chucknorris.R
import blekel.sample.chucknorris.databinding.FragmentJokesBinding
import blekel.sample.chucknorris.di.manager.ComponentManager
import blekel.sample.chucknorris.presentation.jokes.model.JokeListType
import blekel.sample.chucknorris.util.visible
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

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ComponentManager.getInstance().appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jokes, container, false)

        val type = getTypeParam(this)
        // TODO: rm temp
        binding.tvEmpty.text = type.name
        binding.tvEmpty.visible = true

        return binding.root
    }
}
