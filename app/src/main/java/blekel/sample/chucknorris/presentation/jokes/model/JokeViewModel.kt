package blekel.sample.chucknorris.presentation.jokes.model

import android.databinding.ObservableBoolean

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

class JokeViewModel(val id: Long, val text: String) {

    val isShareVisible = ObservableBoolean()
    val isLikeVisible = ObservableBoolean()
    val isDeleteVisible = ObservableBoolean()

    fun onShareClick() {
        // TODO: impl
    }

    fun onLikeClick() {
        // TODO: impl
    }

    fun onDeleteClick() {
        // TODO: impl
    }
}