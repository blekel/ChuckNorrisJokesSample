package blekel.sample.chucknorris.di.manager;

import android.app.Application;
import android.support.annotation.NonNull;

import blekel.sample.chucknorris.di.AppComponent;
import blekel.sample.chucknorris.di.AppModule;
import blekel.sample.chucknorris.di.DaggerAppComponent;

public class ComponentManager implements ComponentProvider {
    private static volatile ComponentManager INSTANCE;
    private final ComponentMap componentMap = new ComponentMap();
    private final AppComponent appComponent;

    private ComponentManager(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    //region Initialization

    public static synchronized void init(Application application) {
        if (INSTANCE != null) {
            throw new IllegalStateException("ComponentManager has already been initialized");
        }
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();

        @SuppressWarnings("UnnecessaryLocalVariable") //need for
                ComponentManager componentManager = new ComponentManager(appComponent);
        INSTANCE = componentManager;
    }

    public static ComponentManager getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("ComponentManager hasn't been initialized");
        }
        return INSTANCE;
    }

    //endregion

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public <T> T addComponent(T component) {
        componentMap.addComponent(component);
        return component;
    }

    public boolean hasComponent(Class<?> clazz) {
        return componentMap.hasComponent(clazz);
    }

    public void removeComponent(Class<?> clazz) {
        componentMap.remove(clazz);
    }

    @NonNull
    @Override
    public <T> T getComponent(Class<T> clazz) {
        if (clazz == AppComponent.class) {
            //noinspection ConstantConditions
            return clazz.cast(getAppComponent());
        }

        return componentMap.getComponent(clazz);
    }
}