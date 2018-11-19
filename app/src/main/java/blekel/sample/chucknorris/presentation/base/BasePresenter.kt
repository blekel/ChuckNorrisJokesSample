package blekel.sample.chucknorris.presentation.base

import android.support.annotation.CallSuper
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Vitaliy Levonyuk on 14.11.2018
 */

abstract class BasePresenter<T : MvpView> : MvpPresenter<T>() {

    private var disposables = CompositeDisposable()

    protected fun addSubscription(disposable: Disposable?) {
        if (disposable != null) {
            disposables.add(disposable)
        }
    }

    @CallSuper
    override fun attachView(view: T) {
        super.attachView(view)
        if (disposables.isDisposed) {
            disposables = CompositeDisposable()
        }
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}