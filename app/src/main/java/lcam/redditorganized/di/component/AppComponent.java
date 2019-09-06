package lcam.redditorganized.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import lcam.redditorganized.SessionManager;
import lcam.redditorganized.base.BaseApplication;
import lcam.redditorganized.di.module.ActivityBuildersModule;
import lcam.redditorganized.di.module.AppModule;
import lcam.redditorganized.di.module.ViewModelFactoryModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class //adding this to appComponent cuz this will be used by all ViewModels in the project
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    SessionManager sessionManager(); //we want this to exist as long as the app is alive

    @Component.Builder
    interface Builder{

        @BindsInstance //available at the time this component is constructed
        Builder application(Application application); //used to bind an app instance to an app component

        AppComponent build();
    }
}
