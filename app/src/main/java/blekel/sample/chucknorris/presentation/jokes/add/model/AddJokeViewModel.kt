package blekel.sample.chucknorris.presentation.jokes.add.model

import android.databinding.ObservableField

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

class AddJokeViewModel {

    var input = ObservableField<String>("")
    var limit = ObservableField<String>("")
}
