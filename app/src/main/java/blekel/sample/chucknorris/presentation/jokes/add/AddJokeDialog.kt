package blekel.sample.chucknorris.presentation.jokes.add

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialog
import android.view.LayoutInflater
import blekel.sample.chucknorris.R
import blekel.sample.chucknorris.databinding.FragmentAddJokeBinding
import blekel.sample.chucknorris.di.manager.ComponentManager
import blekel.sample.chucknorris.presentation.jokes.add.model.AddJokeViewModel
import blekel.sample.chucknorris.util.view.SimpleTextWatcher
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import javax.inject.Inject


/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

class AddJokeDialog : MvpAppCompatDialogFragment(),
    AddJokeContract.View,
    DialogInterface.OnClickListener {

    companion object {
        val TAG = AddJokeDialog::class.java.name

        fun newInstance() = AddJokeDialog()
    }

    @Inject
    @InjectPresenter
    internal lateinit var presenter: AddJokePresenter

    private lateinit var binding: FragmentAddJokeBinding

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ComponentManager.getInstance().appComponent.inject(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity!!
        val inflater = LayoutInflater.from(context)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_joke, null, false
        )

        binding.etInput.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                presenter.onInputChanged()
            }
        })

        return AlertDialog.Builder(context)
            .setTitle("Add new joke")
            .setPositiveButton("Save", this)
            .setNegativeButton("Cancel", this)
            .setView(binding.root)
            .create()
    }

    override fun setModel(model: AddJokeViewModel) {
        binding.model = model
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            AppCompatDialog.BUTTON_POSITIVE -> {
                presenter.onSaveClick()
            }
            AppCompatDialog.BUTTON_NEGATIVE -> {
                presenter.onCancelClick()
            }
        }
    }
}
