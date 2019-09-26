package lcam.redditorganized.di.main;

import dagger.Module;
import dagger.Provides;
import lcam.redditorganized.network.main.MainApi;
import lcam.redditorganized.ui.main.posts.PostsRecyclerAdapter;
import retrofit2.Retrofit;

@Module
public class MainModule {

    //Allows us to inject the adapter as a dependency
    @MainScope
    @Provides
    static PostsRecyclerAdapter provideAdapter(){
        return new PostsRecyclerAdapter();
    }

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }
}
