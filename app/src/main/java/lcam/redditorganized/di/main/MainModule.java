package lcam.redditorganized.di.main;

import dagger.Module;
import dagger.Provides;
import lcam.redditorganized.network.main.MainApi;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }
}
