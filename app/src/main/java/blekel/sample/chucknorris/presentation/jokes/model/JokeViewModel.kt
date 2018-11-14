package blekel.sample.chucknorris.presentation.jokes.model

import android.databinding.ObservableBoolean

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

class JokeViewModel {

    var text = ""
    val isShareVisible = ObservableBoolean()
    val isLikeVisible = ObservableBoolean()
    val isDeleteVisible = ObservableBoolean()

    fun onShareClick() {
    }

    fun onLikeClick() {
    }

    fun onDeleteClick() {
    }
}