package blekel.sample.chucknorris.presentation.jokes.model

import android.databinding.ObservableBoolean
import blekel.sample.chucknorris.domain.Joke
import blekel.sample.chucknorris.presentation.jokes.JokesContract

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

class JokeViewModel(val item: Joke, private val listener: JokesContract.ItemClickListener) {

    val isShareVisible = ObservableBoolean()
    val isLikeVisible = ObservableBoolean()
    val isDeleteVisible = ObservableBoolean()

    fun onShareClick() {
        listener.onShareClick(this)
    }

    fun onLikeClick() {
        listener.onLikeClick(this)
    }

    fun onDeleteClick() {
        listener.onDeleteClick(this)
    }
}