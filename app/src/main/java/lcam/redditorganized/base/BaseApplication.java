package lcam.redditorganized.base;


import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import lcam.redditorganized.di.component.AppComponent;
import lcam.redditorganized.di.component.DaggerAppComponent;

public class BaseApplication extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        //AppComponent component = .builder().application(this).build();
        //component.inject(this);

        return DaggerAppComponent.builder().application(this).build(); //binding an app instance to an app component
    }
}
