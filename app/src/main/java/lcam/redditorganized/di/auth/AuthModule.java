package lcam.redditorganized.di.auth;

import dagger.Module;
import dagger.Provides;
import lcam.redditorganized.network.auth.AuthApi;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    //add dependencies for the Auth SubComponent
    @AuthScope
    @Provides
    static AuthApi provideAuthApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }
}
