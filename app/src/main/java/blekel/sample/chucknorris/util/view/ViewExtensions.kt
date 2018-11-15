package blekel.sample.chucknorris.util.view

import android.view.View

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

var View.visible: Boolean
    get() = this.visibility == View.VISIBLE
    set(value) {
        this.visibility = if (value) View.VISIBLE else View.GONE
    }
