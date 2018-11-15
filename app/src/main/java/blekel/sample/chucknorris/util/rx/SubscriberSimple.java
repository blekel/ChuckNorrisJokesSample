package blekel.sample.chucknorris.util.rx;

import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by Vitaliy Levonyuk on 15.11.2018
 */

public class SubscriberSimple<T> implements BiConsumer<T, Throwable> {

    @Override
    public final void accept(T value, Throwable error) throws Exception {
        if (error == null) {
            onNext(value);
        } else {
            onError(error);
        }
    }

    public void onNext(T value) throws Exception {
        // nop
    }

    public void onError(Throwable error) throws Exception {
        Timber.e(error, "Error");
    }

    public static <T> SubscriberSimple<T> create() {
        return new SubscriberSimple<>();
    }

    public static <T> SubscriberSimple<T> create(final Consumer<T> onNext) {
        return new SubscriberSimple<T>() {
            @Override
            public void onNext(T value) throws Exception {
                onNext.accept(value);
            }
        };
    }

    public static <T> SubscriberSimple<T> create(final Consumer<T> onNext, final Consumer<Throwable> onError) {
        return new SubscriberSimple<T>() {
            @Override
            public void onNext(T value) throws Exception {
                onNext.accept(value);
            }

            @Override
            public void onError(Throwable error) throws Exception {
                onError.accept(error);
            }
        };
    }

    public static <T> SubscriberSimple<T> handleError(final Consumer<Throwable> onError) {
        return new SubscriberSimple<T>() {
            @Override
            public void onError(Throwable error) throws Exception {
                onError.accept(error);
            }
        };
    }
}