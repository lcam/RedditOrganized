package lcam.redditorganized.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import lcam.redditorganized.di.auth.AuthModule;
import lcam.redditorganized.di.auth.AuthScope;
import lcam.redditorganized.di.auth.AuthViewModelsModule;
import lcam.redditorganized.di.main.MainFragmentBuildersModule;
import lcam.redditorganized.di.main.MainModule;
import lcam.redditorganized.di.main.MainScope;
import lcam.redditorganized.di.main.MainViewModelsModule;
import lcam.redditorganized.ui.auth.AuthActivity;
import lcam.redditorganized.ui.main.MainActivity;

@Module
public abstract class ActivityBuildersModule {
    //only for Activity declarations, these are subcomponents

    @AuthScope //AuthActivity subcomponent owns the AuthScope
    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class, AuthModule.class} //creating dependencies that can only be accessed inside this subcomponent
    ) //AuthActivity is now a potential client that I can inject dependencies into
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuildersModule.class, MainViewModelsModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity(); //adds MainActivity as a subcomponent -> generates subcomponent code
}
