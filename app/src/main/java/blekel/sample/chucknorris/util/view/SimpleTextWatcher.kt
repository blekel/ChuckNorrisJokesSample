package blekel.sample.chucknorris.util.view

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

open class SimpleTextWatcher : TextWatcher {

    override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(input: Editable) {
    }
}