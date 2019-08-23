package lcam.redditorganized.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import lcam.redditorganized.di.auth.AuthViewModelsModule;
import lcam.redditorganized.ui.auth.AuthActivity;

@Module
public abstract class ActivityBuildersModule {
    //only for Activity declarations

    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class} //Now, only the AuthActivity SubComponent will be able to use this ViewModel
    ) //AuthActivity is now a potential client that I can inject dependencies into
    abstract AuthActivity contributeAuthActivity();
}
