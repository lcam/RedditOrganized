package lcam.redditorganized.base;


import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import lcam.redditorganized.di.application.DaggerAppComponent;

public class BaseApplication extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

        return DaggerAppComponent.builder().application(this).build(); //binding an app instance to an app component
    }
}
