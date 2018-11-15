package blekel.sample.chucknorris.util.view

import android.databinding.BindingAdapter
import android.view.View

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, isVisible: Boolean) {
        view.visible = isVisible
    }
}
