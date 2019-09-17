package lcam.redditorganized.di.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import lcam.redditorganized.ui.main.posts.PostsFragments;
import lcam.redditorganized.ui.main.profile.ProfileFragment;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragments contributePostsFragment();
}
