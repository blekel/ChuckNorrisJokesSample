package blekel.sample.chucknorris.presentation.jokes.add

import blekel.sample.chucknorris.presentation.base.BasePresenter
import blekel.sample.chucknorris.presentation.jokes.add.model.AddJokeViewModel
import com.arellomobile.mvp.InjectViewState
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

@InjectViewState
class AddJokePresenter @Inject constructor(
) : BasePresenter<AddJokeContract.View>(), AddJokeContract.Presenter {

    private val model = AddJokeViewModel()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        onInputChanged()
        viewState.setModel(model)
    }

    override fun onInputChanged() {
        val inputText = model.input.get()!!
        val length = inputText.length

        model.limit.set("$length / ${AddJokeContract.INPUT_LIMIT}")
    }

    override fun onSaveClick() {
        val inputText = model.input.get()!!
        Timber.d("new joke: $inputText")
    }

    override fun onCancelClick() {
        // nop
    }
}
