package lcam.redditorganized.di.component;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import lcam.redditorganized.base.BaseApplication;
import lcam.redditorganized.di.module.ActivityBuildersModule;
import lcam.redditorganized.di.module.AppModule;

@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{

        @BindsInstance //available at the time this component is constructed
        Builder application(Application application); //used to bind an app instance to an app component

        AppComponent build();
    }
}
