package lcam.redditorganized.di.main;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import lcam.redditorganized.network.main.MainApi;
import lcam.redditorganized.network.main.MainNetworkClient;
import lcam.redditorganized.network.main.RequestProfile;
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
    static MainApi provideMainApi(@Named("mainRetrofit") Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }

    @MainScope
    @Provides
    static RequestProfile provideAuthenticatedUser(MainNetworkClient client){
        return new RequestProfile(client);
    }

    @MainScope
    @Provides
    static MainNetworkClient provideMainNetworkClient(MainApi mainApi){
        return new MainNetworkClient(mainApi);
    }
}
