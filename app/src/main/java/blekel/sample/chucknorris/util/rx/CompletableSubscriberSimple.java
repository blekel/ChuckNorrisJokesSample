package blekel.sample.chucknorris.util.rx;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */
public class CompletableSubscriberSimple implements CompletableObserver {

    @Override
    public void onSubscribe(Disposable d) {
        // nop
    }

    @Override
    public void onComplete() {
        // nop
    }

    @Override
    public void onError(Throwable e) {
        Timber.e(e);
    }

    public static CompletableSubscriberSimple create() {
        return new CompletableSubscriberSimple();
    }

    public static CompletableSubscriberSimple create(final Runnable onComplete) {
        return new CompletableSubscriberSimple() {
            @Override
            public void onComplete() {
                if (onComplete != null) {
                    onComplete.run();
                }
            }
        };
    }
}
