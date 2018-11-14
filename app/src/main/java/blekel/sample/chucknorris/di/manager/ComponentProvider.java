package blekel.sample.chucknorris.di.manager;

import android.support.annotation.NonNull;

public interface ComponentProvider {
    @NonNull
    <T> T getComponent(Class<T> clazz);
}
