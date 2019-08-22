package lcam.redditorganized.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import lcam.redditorganized.base.AuthActivity;

@Module
public abstract class ActivityBuildersModule {
    //only for Activity declarations

    @ContributesAndroidInjector //AuthActivity is now a potential client that I can inject dependencies into
    abstract AuthActivity contributeAuthActivity();
}
