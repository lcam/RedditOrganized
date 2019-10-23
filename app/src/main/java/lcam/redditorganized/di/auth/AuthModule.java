package lcam.redditorganized.di.auth;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import lcam.redditorganized.network.auth.AuthApi;
import lcam.redditorganized.network.auth.AuthNetworkClient;
import lcam.redditorganized.network.auth.AuthenticateUser;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    //add dependencies for the Auth SubComponent
    @AuthScope
    @Provides
    static AuthApi provideAuthApi(@Named("authRetrofit") Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }

    @AuthScope
    @Provides
    static AuthenticateUser provideAuthenticatedUser(AuthNetworkClient client){
        return new AuthenticateUser(client);
    }

    @AuthScope
    @Provides
    static AuthNetworkClient provideAuthNetworkClient(AuthApi authApi){
        return new AuthNetworkClient(authApi);
    }
}
